(ns home-automation.core
  (:require [clojure.data.json :as json])
  (:require [clojure.tools.logging :as log])
  (:require [clojure.string :as string])
  (:require [clojurewerkz.machine-head.client :as mh])
  (:gen-class))

(import java.lang.Thread)
(import java.lang.Long)
(import org.kie.api.KieServices)
(import org.kie.api.event.rule.RuleRuntimeEventListener)

(defn get-fact-type [ks klass] (.getFactType (.getKieBase ks) "home_automation.rules" klass))

(defn create-event
  [ks source action]
  (let [fact-type (get-fact-type ks "Event")
        instance (.newInstance fact-type)]
    (.setFromMap fact-type instance {"source" source "action" action})
    instance))

(defn is-action [ks obj] (= (type obj) (.getFactClass (get-fact-type ks "Action"))))

(defn init-kie-container
  "Creates a new KIE container"
  []
  (.newKieClasspathContainer (KieServices/Factory/get)))
  
(defn init-knowledge-session
  "Creates a stateful knowledge session from a KIE session"
  [kie-container]
  (.newKieSession kie-container))

(defn ks-add-rule-runtime-event-listener
  "Adds an objectInserted callback to a KieSession"
  [ks object-inserted-fn object-updated-fn object-deleted-fn]
  (.addEventListener ks (reify RuleRuntimeEventListener
                          (objectInserted [this evt] (object-inserted-fn evt))
                          (objectUpdated [this evt] (object-updated-fn evt))
                          (objectDeleted [this evt] (object-deleted-fn evt))))
  ks)

(defn on-message [ks topic payload-raw]
  (let [action ((json/read-str (String. payload-raw "UTF-8")) "action")
        source (string/replace topic "zigbee2mqtt/" "")
        event (create-event ks source action)]
    (log/debug "Received event: " event)
    (.insert ks event)))

(defn on-action [mqtt-conn obj] (println "OBJ: " obj))

(def mqtt-url "tcp://localhost:1883")
(def mqtt-topic "zigbee2mqtt")
(defn -main
  [& args]
  (log/infof "Connecting to mqtt on %s..." mqtt-url)
  (log/info "Initialising Drools")
  (let [ks (init-knowledge-session (init-kie-container))
        mqtt-conn (mh/connect "tcp://localhost:1883")
        ks-cb (fn [evt] (let [obj (.getObject evt)] (when (is-action obj) (on-action mqtt-conn obj))))
        mqtt-cb (fn [topic _ payload] (on-message ks topic payload))]
    (mh/subscribe mqtt-conn { (str mqtt-topic "/#") 0 } mqtt-cb)
    (ks-add-rule-runtime-event-listener ks ks-cb (constantly nil) (constantly nil))
    (log/info "Listening on mqtt to topic" (str mqtt-topic "/#"))
    (log/debug "Sleeping...")
    (Thread/sleep Long/MAX_VALUE))) ;TODO learn a better way to sleep forever
    


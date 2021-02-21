(ns home-automation.core
  (:gen-class))

(import org.kie.api.KieServices)

(defn init-kie-container
  "Creates a new KIE container"
  []
  (.newKieClasspathContainer (KieServices/Factory/get)))
  
(defn init-knowledge-session
  "Creates a stateful knowledge session from a KIE session"
  [kie-container]
  (.newKieSession kie-container))

(defn fire-rules-on-event
  "Fire rules with a given event, and return the result"
  [ks, event]
  (.fireAllRules ks { :event event }))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, clojure")
  (let [ks (init-knowledge-session (init-kie-container))]
       (.insert ks {:a "hello"})
       (.fireAllRules ks)
       (println "Bye")))


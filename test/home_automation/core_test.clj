(ns home-automation.core-test
  (:require [clojure.test :refer :all]
            [home-automation.core :refer :all]))

(def kie-container (init-kie-container))

(defn create-event
  [ks source action]
  (let [fact-type (.getFactType (.getKieBase kie-container) "home_automation.rules" "Event")
        instance (.newInstance fact-type)]
    (.setFromMap fact-type instance { "source" source "action" action })
    instance))

(deftest init-knowledge-session-test
  (testing "Initialisation of the KIE container"
    (is (some? (init-knowledge-session kie-container)))))

(deftest create-event-test
  (testing "Create an Event instance declared in Drools"
    (is (some? (create-event (init-knowledge-session kie-container) "x.y.z" "hello")))))
      

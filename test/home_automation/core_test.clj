(ns home-automation.core-test
  (:require [clojure.test :refer :all]
            [home-automation.core :refer :all]))

(def kie-container (init-kie-container))

(defn test-event-subject
  [evt expected-subject]
  (let [subject (.getSubject (.getObject evt))]
    (= subject expected-subject)))

(defn test-event-action
  [evt expected-action]
  (let [action (.getAction (.getObject evt))]
    (= action expected-action)))

(deftest init-knowledge-session-test
  (testing "Initialisation of the KIE container"
    (is (some? (init-knowledge-session kie-container)))))

(deftest create-event-test
  (testing "Create an Event instance declared in Drools"
    (is (some? (create-event (init-knowledge-session kie-container) "x.y.z" "hello")))))

(deftest action-on-event-test
  (testing "Create an Event, fire rules for it and get the action for it"
    (let [ks (init-knowledge-session kie-container)
          evt (create-event ks "dolgozo.iroasztal.kapcsolo" "on")
          cb (fn [evt] (let [o (.getObject evt)]
                         (is (test-event-subject evt "dolgozo.iroasztal.lampa"))
                         (is (test-event-action evt "on"))))]
      (.insert ks evt)
      (ks-add-rule-runtime-event-listener ks cb (constantly nil) (constantly nil))
      (.fireAllRules ks))))



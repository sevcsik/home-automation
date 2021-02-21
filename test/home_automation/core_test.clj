(ns home-automation.core-test
  (:require [clojure.test :refer :all]
            [home-automation.core :refer :all]))

(def kie-container (init-kie-container))

(deftest init-knowledge-session-test
  (testing "Initialisation of the KIE container"
    (is (some? (init-knowledge-session kie-container) true))))

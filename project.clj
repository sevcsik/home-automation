(defproject home-automation "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.0"]
                 ;; https://mvnrepository.com/artifact/org.drools/drools-compiler
                 [org.drools/drools-compiler "7.50.0.t20210209"]
                 ;; https://mvnrepository.com/artifact/org.slf4j/slf4j-api
                 [org.slf4j/slf4j-api "1.7.30"]
                 ;; https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12
                 [org.slf4j/slf4j-log4j12 "1.7.30"]
                 ;; https://mvnrepository.com/artifact/org.drools/drools-mvel
                 [org.drools/drools-mvel "7.50.0.t20210209"]]


  :main ^:skip-aot home-automation.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

(defproject home-automation "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [;; https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [ch.qos.logback/logback-core "1.2.3"]
                 [clojurewerkz/machine_head "1.0.0"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "2.0.2"]
                 [org.clojure/tools.logging "1.1.0"]
                 ;; https://mvnrepository.com/artifact/org.drools/drools-compiler
                 [org.drools/drools-compiler "7.50.0.t20210209"]
                 ;; https://mvnrepository.com/artifact/org.slf4j/slf4j-api
                 [org.slf4j/slf4j-api "1.7.30"]
                 ;; https://mvnrepository.com/artifact/org.drools/drools-mvel
                 [org.drools/drools-mvel "7.50.0.t20210209"]]
  :exclusions [;; Exclude transitive dependencies on all other logging
               ;; implementations, including other SLF4J bridges.
               commons-logging
               log4j
               org.apache.logging.log4j/log4j
               org.slf4j/simple
               org.slf4j/slf4j-jcl
               org.slf4j/slf4j-nop
               org.slf4j/slf4j-log4j12
               org.slf4j/slf4j-log4j13]
  :jvm-opts ["-Dclojure.tools.logging.factory=clojure.tools.logging.impl/slf4j-factory"]
  :main ^:skip-aot home-automation.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

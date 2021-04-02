(defproject agent "0.0.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/core.async "1.3.610"]
                 [org.clojure/tools.cli "1.0.206"]
                 [org.clojure/tools.logging "1.1.0"]
                 [nrepl/nrepl "0.8.3"]
                 [http-kit "2.4.0"]
                 [cheshire "5.10.0"]
                 [ring/ring-core "1.9.2"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.5.1" :exclusions [cheshire]]
                 [metosin/ring-http-response "0.9.1"]
                 [ch.qos.logback/logback-classic "1.2.3"]
                 [bouncer "1.0.1" :exclusions [org.clojure/clojurescript]]
                 [compojure "1.6.2"]
                 [slingshot "0.12.2"]
                 [mount "0.1.16"]
                 [cprop "0.1.16"]
                 [org.jsoup/jsoup "1.12.2"]
                 [cambium/cambium.core "1.0.0"]
                 [cambium/cambium.codec-cheshire "1.0.0"]
                 [cambium/cambium.logback.json   "0.4.4"]]

  ;; :global-vars {*warn-on-reflection* true
  ;;               *assert* true}

  :java-source-paths ["java-src"]
  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :main agent.server
  :profiles
  {:uberjar {:omit-source true
             :uberjar-name "agent.jar"
             :uberjar-exclusions [#"META-INF/(leiningen|maven)"]
             :aot :all
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}


   :dev    [:project/dev]
   :prod   [:project/dev :project/prod]

   :project/dev {:dependencies [[prone "1.6.4"]
                                [ring-logger "1.0.1"]
                                [ring-cors "0.1.13"]
                                [ring/ring-mock "0.4.0"]
                                [ring/ring-devel "1.7.1"]
                                [pjstadig/humane-test-output "0.10.0"]
                                [ragtime "0.8.0"]]
                 :plugins [[com.jakemccrary/lein-test-refresh "0.24.1"]]

                 :source-paths ["env/dev/clj"]
                 :resource-paths ["env/dev/resources"]
                 :repl-options {:init-ns user}
                 :injections [(require 'pjstadig.humane-test-output)
                              (pjstadig.humane-test-output/activate!)]}
   :project/prod  {:resource-paths ["env/prod/resources"]}})

(ns user
  (:require [mount.core :as mount]
            [agent.config :refer [env]]
            agent.server))

(defn start []
  (mount/start-without #'agent.server/repl-server))

(defn stop []
  (mount/stop-except #'agent.server/repl-server))

(defn restart []
  (stop)
  (start))

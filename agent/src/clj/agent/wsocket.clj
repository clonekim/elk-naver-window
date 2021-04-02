(ns agent.wsocket
  (:require [clojure.tools.logging :as log]
            [org.httpkit.server :refer [send!
                                        with-channel
                                        on-close on-receive]]))


(def clients (atom #{}))


(defn notify-clients [msg]
  (log/info "==>" msg)
  (doseq [client @clients]
    (send! client msg)))



(defn process-handler [req]
  (with-channel req channel
    (log/info channel "connected!")
    (swap! clients conj channel)
    (on-receive channel #'notify-clients)
    (on-close channel (fn [status]
                        (swap! clients #(remove #{channel} %))
                        (log/info channel "closed, status" status)))))

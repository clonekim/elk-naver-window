(ns agent.command
  (:use agent.common)
  (:import [org.jsoup Jsoup])
  (:require [cambium.core :as log]
            [cambium.mdc :refer [with-raw-mdc]]
            [clojure.core.async :as async]
            [clojure.string :as str]
            [clj-time.format :as f]
            [cheshire.core :refer [parse-string generate-string]]
            [org.httpkit.client :as http]
            [agent.util :as util]
            [agent.common :refer [replace-products]]
            [agent.wsocket :refer [notify-clients]]))


(def user-agent "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Mobile Safari/537.36")
(def scrap-top-url "https://shopping.naver.com/v1/products?_nc_=1617289200000&subVertical=STYLE&page=%d&pageSize=%d&sort=POPULARITY&filter=ALL&displayType=CATEGORY_HOME&includeZzim=true&includeViewCount=true&includeStoreCardInfo=true&includeStockQuantity=false&includeBrandInfo=false&includeBrandLogoImage=false&includeRepresentativeReview=false&includeListCardAttribute=false&includeRanking=false&includeRankingByMenus=false&includeStoreCategoryName=false&includeIngredient=false&menuId=%s&filterTodayDelivery=false")


(declare scan-women)
(defmulti scan (fn [params] (keyword (:type params))))



(defn scanner [url page page-size store-id]
  (:products (connect-url (format url page page-size store-id))))


(defmethod scan :women [{:keys [buffer maxitem menuid]}]
  (let [d (promise)
        ch (async/chan 10)
        _     (async/go-loop [accum 0 page 1]
                (let [coll (scanner scrap-top-url page buffer menuid)]
                  (if (or (empty? coll) (>= accum maxitem))
                    (deliver d true)
                    (do
                      (async/put! ch coll)
                      (recur (+ accum (count coll)) (inc page))))))]

    (async/go-loop []
      (let [c (async/<! ch)]
        (with-raw-mdc {"filename" "logstash"}
          (doseq [item (replace-products c)]
            (notify-clients (generate-string {:data item}))
            (log/info item "stored!")))
        (recur)))

    (async/go
      (when @d
        (notify-clients (generate-string {:status "FIN"}))))))






(defn scan-women [params]
  (scan (assoc params :type :women)))

(defn collect! [params]
  (scan-women params))

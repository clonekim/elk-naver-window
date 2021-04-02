(ns agent.common
  (:require [clojure.tools.logging :as log]
            [clojure.string :as str :refer [blank?]]
            [clj-time.local :as l]
            [clj-time.format :as f]
            [org.httpkit.client :as http]
            [cheshire.core :refer [parse-string generate-string]]
            [agent.util :as util]))

(defn connect-url [url]
  (log/debug "Connecting..." url)
  (let [{:keys [status body]} @(http/get url)]
    (if (or (<= 200 status) (< status 300))
      (parse-string body true))))


(defn chunk-by-size [coll size]
  (loop [chunk-group [] iter coll]
    (if (empty? iter)
      chunk-group
      (recur
       (conj chunk-group (take size iter))
       (drop size iter)))))

(def custom-formatter (f/formatter "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))


(defn to-date [^String str]
  (if (blank? str)
    nil
    (util/parse-date
     (l/to-local-date-time str))))

(defn re-num [^String str]
  (if (blank? str)
    0
    (-> (re-seq #"[0-9]*" str)
        (str/join)
        (util/parse-int))))

(defn to-tags [param]
   (->> (map #(:name %) (-> param :tags))
          (into #{})
          (str/join ",")))


(defn to-images [param]
  (-> (map #(:imageUrl %) (-> param :images))
      generate-string))


(defn find-max-menu [param]
  (->> (apply max
              (->> param
                   :menus
                   (map util/parse-int)))))



(defn replace-products [coll]
  (mapv #(assoc {}
                :product_no (:productNo %)
                :product_code (:_id %)
                :store_id (-> % :channel :_id)
                :naver_category (-> % :naverShoppingCategory :name)
                :view_count_from_window (:viewCountFromWindowApi %)
                :best (:best %)
                :npay (:npay %)
                :exposure (:exposure %)
                :mart_updated_at (to-date (:martUpdatedAt %))
                :name (:name %)
                :mobile_discount_price (:mobileDiscountPrice % 0)
                :pc_discount_price (:pcDiscountPrice % 0)
                :recent_sale_count (:recentSaleCount % 0)
                :total_sale_count (:totalSaleCount % 0)
                :sale_price (:salePrice % 0)
                :soldout (:soldout % 0)
                :tags (to-tags %)
                :is_new_item (:isNewItem %)
                :content_text (:contentText %)
                :channel_name (-> % :channel :name)
                :style_name  (-> % :channel :storeCategoryRelations first :frontExposureName)
                :stock_quantity (:stockQuantity % 0)
                :images (to-images %)
                :average_review_score (:averageReviewScore % 0)
                :popular_score (:popularScore % 0)
                :created_at (to-date (:createdAt %))) coll)
  )

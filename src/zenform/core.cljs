(ns zenform.core
  (:require [cljs.pprint :as pprint]
            
            
            [re-frame.core :as rf]
            
            [zenform.model]
            [zenform.events]))

(defn form-value [form-path]
  (let [v (rf/subscribe [:zf/get-value form-path])]
    (fn []
      [:pre (with-out-str (pprint/pprint @v))])))

(defn node [form-path path]
  (let [v (rf/subscribe [:zf/node form-path path])]
    (fn []
      [:pre (with-out-str (pprint/pprint @v))])))

(defn pprint [v]
  [:pre (with-out-str (pprint/pprint v))])

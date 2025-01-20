(ns zenform.events
    (:require [re-frame.core :refer [reg-event-fx]]
              
              [zenform.model :as zf]))

(reg-event-fx
 :zf/eval-form
 (fn [{db :db} [_ form-path {:keys [data success error]}]]
   (let [form-data                   (get-in db form-path)
         {:keys [errors value form]} (zf/eval-form form-data)]
     (cond-> {:db (assoc-in db form-path (assoc form :errors errors))
              :fx []}

       (and (empty? errors) success)
       (assoc :dispatch [(:event success) (assoc (:params success) :data (merge data {:form-data  form
                                                                                      :form-value value}))])

       (seq errors)
       ((completing
         #?(:clj  (println errors)
            :cljs (.warn js/console "Form errors: " (clj->js errors)))))

       (and (seq errors) error)
       (update :fx conj [:dispatch [(:event error) (assoc (:params error) :errors errors)]])))))

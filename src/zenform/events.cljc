(ns zenform.events
  (:require [re-frame.core :as rf]
            [zenform.node :as node]))

(rf/reg-event-db
 :zf/init-form
 (fn [db [_ form form-path]]
   (assoc-in db form-path form)))

(rf/reg-event-db
 :zf/on-input
 (fn [db [_ form-path field-path value]]
   (update db form-path node/trigger-input value)))

(rf/reg-event-db
 :zf/on-change
 (fn [db [_ form-path field-path value]]
   (update db form-path node/trigger-value value)))

(rf/reg-event-db
 :zf/set-value
 (fn [db [_ form-path value]]
   (update-in db form-path node/set-value value)))

(rf/reg-event-db
 :zf/validate
 (fn [db [_ form-path]]
   (update-in db form-path node/validate-all)))

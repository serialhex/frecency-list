(ns lt.plugins.frecency-list
  (:require [lt.object :as object]
            [lt.objs.tabs :as tabs]
            [lt.objs.command :as cmd])
  (:require-macros [lt.macros :refer [defui behavior]]))

(defui hello-panel [this]
  [:h1 "Hello from frecency-list"])

(object/object* ::frecency-list.hello
                :tags [:frecency-list.hello]
                :name "frecency-list"
                :init (fn [this]
                        (hello-panel this)))

(behavior ::on-close-destroy
          :triggers #{:close}
          :reaction (fn [this]
                      (when-let [ts (:lt.objs.tabs/tabset @this)]
                        (when (= (count (:objs @ts)) 1)
                          (tabs/rem-tabset ts)))
                      (object/raise this :destroy)))

(def hello (object/create ::frecency-list.hello))

(cmd/command {:command ::say-hello
              :desc "frecency-list: Say Hello"
              :exec (fn []
                      (tabs/add-or-focus! hello))})

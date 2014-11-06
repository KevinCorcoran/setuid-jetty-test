(ns setuid-jetty-test.setuid-jetty-test-web-service
  (:require [compojure.core :as compojure]
            [compojure.route :as route]
            [puppetlabs.trapperkeeper.core :as trapperkeeper]))

(def app
  (compojure/routes
    (compojure/GET "/hello" []
                   (fn [req]
                     (println "My UID will change in 10 seconds...")
                     (future
                       (Thread/sleep 10000)
                       (LibC/setuid 501)
                       (println "SUCCESFULLY CHANGED MY UID"))
                     {:status  200
                      :body    "ok"}))
    (route/not-found "Not Found")))

(trapperkeeper/defservice hello-web-service
  [[:WebserverService add-ring-handler]]

  (init [this context]
    (add-ring-handler app "/")
    context))

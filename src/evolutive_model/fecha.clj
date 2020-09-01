(ns evolutive-model.fecha
  (:require [clojure.spec.alpha :as s]))

(defn fecha-2-timestamp [fecha]
  (.getTime (new java.util.Date
                 (- (::anio fecha) 1900)
                 (- (::mes fecha) 1)
                 (::dia fecha))))
(s/def ::mes
  (s/and pos-int? (s/int-in 1 13)))

(s/def ::date
  (s/keys :req [::dia pos-int?
                ::mes ::mes
                ::anio pos-int?]))

(s/fdef fecha
  :args (s/cat ::dia pos-int?
               ::mes ::mes
               ::anio pos-int?)
  :ret ::date)

(defn fecha [dia mes anio]
  {::dia dia ::mes mes ::anio anio})

(defn mayor [fecha1 fecha2]
  (> (fecha-2-timestamp fecha1) (fecha-2-timestamp fecha2)))


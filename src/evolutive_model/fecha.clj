(ns evolutive-model.fecha
  (:require [clojure.spec.alpha :as s]))

(s/def ::mes
  (s/and pos-int? (s/int-in 1 13)))

(s/def ::fecha
  (s/keys :req [::dia pos-int?
               ::mes ::mes
               ::anio pos-int?]))

(s/fdef fecha
  :args (s/cat :dia pos-int?
               :mes ::mes
               :anio pos-int?)
  :ret ::fecha)

(defn fecha [dia mes anio]
  {::dia dia ::mes mes ::anio anio})

(defn mayor [fecha1 fecha2]
  (> (::anio fecha1) (::anio fecha2)))

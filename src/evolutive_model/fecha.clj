(ns evolutive-model.fecha
  (:require [clojure.spec.gen.alpha :as gen]
  (:require [clojure.spec.alpha :as s]))


(defn fecha-2-java [fecha]
  (new java.util.Date
                 (- (::anio fecha) 1900)
                 (- (::mes fecha) 1)
                 (::dia fecha)))

(defn fecha-2-timestamp [fecha]
  (.getTime (new java.util.Date
                 (- (::anio fecha) 1900)
                 (- (::mes fecha) 1)
                 (::dia fecha))))

(s/def ::dia pos-int?)

(s/def ::mes
  (s/and pos-int? (s/int-in 1 13)))

(s/def ::anio pos-int?)

(s/def ::date
  (s/keys :req [::dia ::mes ::anio]))

(s/fdef fecha
  :args (s/cat ::dia pos-int?
               ::mes ::mes
               ::anio pos-int?)
  :ret ::date)

(defn mayor [fecha1 fecha2]
  (> (fecha-2-timestamp fecha1) (fecha-2-timestamp fecha2)))

(defn mismo-dia [fecha numero-dia]
  (=
   numero-dia
   (.getDay (fecha-2-java fecha))))


(defn fecha [dia mes anio]
  {::dia dia ::mes mes ::anio anio})

(defn dia-mes-anio-valido? [dia mes anio]
  true)

(def generador-fecha
  (gen/fmap #(apply fecha %)
            (gen/such-that
             #(apply dia-mes-anio-valido? %)
             (gen/tuple
              (s/gen (s/and pos-int? (s/int-in 1 32)))
              (s/gen (s/and pos-int? (s/int-in 1 13)))
              (s/gen pos-int?)))))

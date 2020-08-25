(ns evolutive-model.main
  (:require [clojure.spec.alpha :as s])
  (:gen-class))

(s/def ::mes
  (s/and pos-int? (s/int-in 1 13)))

(defn fecha [dia mes anio]
  {::dia dia ::mes mes ::anio anio})

(s/def ::fecha
  (s/keys :req [::dia pos-int?
               ::mes ::mes
               ::anio pos-int?]))

(s/fdef fecha
  :args (s/cat :dia pos-int?
               :mes ::mes
               :anio pos-int?)
  :ret ::fecha)

(defn mismo-clave [fecha1 fecha2 clave]
  (= (clave fecha1) (clave fecha2)))

(defn puntual [dia mes anio]
  {:pre [(s/valid? ::mes mes)]}
  {:Tipo :Puntual :dia dia :mes mes :anio anio})

(defn anual [dia mes]
  {:Tipo :Anual :dia dia :mes mes})

(defmulti feriado? :Tipo)
(defmethod feriado? :Puntual [feriado fecha-consulta]
   (= feriado fecha-consulta))
(defmethod feriado? :Anual [feriado fecha-consulta]
  (and
   (mismo-clave feriado fecha-consulta :dia)
   (mismo-clave feriado fecha-consulta :mes)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

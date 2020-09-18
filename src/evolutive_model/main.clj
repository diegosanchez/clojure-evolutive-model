(ns evolutive-model.main
  (:require [evolutive-model.fecha :as f])
  (:require [clojure.spec.alpha :as s])
  (:gen-class))

(defn mismo-clave [fecha1 fecha2 clave]
  (= (clave fecha1) (clave fecha2)))

(defn puntual [dia mes anio]
  {:pre [(s/valid? :evolutive-model.fecha/mes mes)]}
  {:Tipo :Puntual
   :dia dia :mes mes :anio anio})

(defn anual [dia mes]
  {:Tipo :Anual
   :dia dia :mes mes})

(defn desde [dia mes anio]
  {:Tipo  :Desde
   :desde (f/fecha dia mes anio)})

(defn semanal []
  {:Tipo :Semanal
   :dia-semana 0})

(defmulti feriado? :Tipo)

(defmethod feriado? :Puntual [feriado fecha-consulta]
   (= feriado fecha-consulta))

(defmethod feriado? :Anual [feriado fecha-consulta]
  (and
   (mismo-clave feriado fecha-consulta :dia)
   (mismo-clave feriado fecha-consulta :mes)))

(defmethod feriado? :Desde [feriado fecha-consulta]
  (f/mayor fecha-consulta (:desde feriado)))

(defmethod feriado? :Semanal [feriado fecha-consulta]
  (f/mismo-dia fecha-consulta (:dia-semana feriado)))

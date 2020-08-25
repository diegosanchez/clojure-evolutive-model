(ns evolutive-model.main
  (:require [clojure.spec.alpha :as s])
  (:gen-class))

(s/def ::mes
  (s/and pos-int? (s/int-in 1 13)))

(defn fecha [dia mes anio]
  {:pre [(s/valid? ::mes mes)]}
  {:dia dia :mes mes :anio anio})

(defn feriado-puntual [dia mes anio]
  (fecha dia mes anio))

(defn feriado-anual [dia mes]
  {:dia dia :mes mes})

(defn es-feriado?
  [fecha-consulta feriados]
  (some #(= fecha-consulta %)feriados))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(ns evolutive-model.main
  (:require [clojure.spec.alpha :as s])
  (:gen-class))

(s/def ::mes
  (s/and pos-int? (s/int-in 1 13)))

(defn feriado-puntual [dia mes anio]
  {:pre [(s/valid? ::mes mes)]}
  {:dia dia :mes mes :anio anio})

(defn es-feriado?
  [fecha-consulta feriados]
  (some #(= fecha-consulta %)feriados))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

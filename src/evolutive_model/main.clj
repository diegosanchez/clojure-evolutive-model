(ns evolutive-model.main
  (:gen-class))

(defn es-feriado?
  [fecha-consulta feriados]
  (some #(= fecha-consulta %)feriados))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

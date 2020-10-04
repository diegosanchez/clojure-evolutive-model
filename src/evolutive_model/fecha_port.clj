(ns evolutive-model.fecha-port)

(defn fecha-2-java
  [anio mes dia]
  (new java.util.Date (- anio 1900) (- mes 1) dia))

(defn fecha-2-timestamp
  [anio mes dia]
  (.getTime (fecha-2-java anio
                          mes
                          dia)))

(defn mismo-dia
  [anio mes dia numero-dia]
  (= numero-dia
     (.getDay (fecha-2-java anio
                            mes
                            dia))))


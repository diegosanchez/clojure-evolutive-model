(ns evolutive-model.fecha
  (:require [clojure.spec.gen.alpha :as gen]
            [clojure.spec.alpha :as s]))

(defn mes-con-31-dias?
  [mes]
  (not (nil? (some #(= mes %) '(1 3 5 7 10 12)))))

(defn mes-con-30-dias?
  [mes]
  (not (nil? (some #(= mes %) '(4 6 8 11)))))

(defn anio-bisiesto?
  [anio]
  (or
   (zero? (mod anio 400))
   (zero? (mod anio 4))))

(defn mes-con-maximo-de-dias
  [maximo]
  (s/int-in 1 (+ maximo 1)))

(s/def ::mes-spec
  (s/int-in 1 13))

(s/def ::anio-spec
  (s/int-in 1900 2400))

(defn dia-spec
  [anio mes]
  (cond
    (mes-con-30-dias? mes)       (mes-con-maximo-de-dias 30)
    (mes-con-31-dias? mes)       (mes-con-maximo-de-dias 31)
    (anio-bisiesto? anio)        (mes-con-maximo-de-dias 29)
    (not (anio-bisiesto? anio))  (mes-con-maximo-de-dias 28)))

(defn fecha [anio mes dia]
  {:pre [(and
          (s/valid? ::anio-spec anio)
          (s/valid? ::mes-spec mes)
          (s/valid? (dia-spec anio mes) dia))]}
  {:dia dia :mes mes :anio anio})

(defn fecha-2-java [fecha]
  (new java.util.Date
       (- (::anio-spec fecha) 1900)
       (- (::mes-spec fecha) 1)
       (::dia fecha)))

(defn fecha-2-timestamp [fecha]
  (.getTime (new java.util.Date
                 (- (::anio-spec fecha) 1900)
                 (- (::mes-spec fecha) 1)
                 (::dia fecha))))

(defn mayor [fecha1 fecha2]
  (> (fecha-2-timestamp fecha1) (fecha-2-timestamp fecha2)))

(defn mismo-dia [fecha numero-dia]
  (=
   numero-dia
   (.getDay (fecha-2-java fecha))))

(def gen-anio-mes-dia
  (gen/bind
    (gen/tuple
     (s/gen ::anio-spec)
     (s/gen ::mes-spec))
    (fn [lst]
      (let [[anio mes] lst]
        (gen/tuple
         (gen/return anio)
         (gen/return mes)
         (s/gen (dia-spec anio mes)))))))

          
(def gen-fecha
  (gen/fmap #(apply fecha %) gen-anio-mes-dia))


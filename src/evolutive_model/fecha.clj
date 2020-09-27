(ns evolutive-model.fecha
  (:require [clojure.spec.gen.alpha :as gen]
            [clojure.spec.alpha :as s]))

(s/def ::mes
  (s/int-in 1 13))

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

(defn fecha [anio mes dia]
  {:pre [(and
          (s/valid? ::mes mes)
          (or
           (and (mes-con-31-dias? mes)         (s/valid? (mes-con-maximo-de-dias 31) dia))
           (and (mes-con-30-dias? mes)         (s/valid? (mes-con-maximo-de-dias 30) dia))
           (and (comp not anio-bisiesto? anio) (s/valid? (mes-con-maximo-de-dias 28) dia))
           (and (anio-bisiesto? anio)          (s/valid? (mes-con-maximo-de-dias 29) dia))))]}
  {:dia dia :mes mes :anio anio})














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

(defn mayor [fecha1 fecha2]
  (> (fecha-2-timestamp fecha1) (fecha-2-timestamp fecha2)))

(defn mismo-dia [fecha numero-dia]
  (=
   numero-dia
   (.getDay (fecha-2-java fecha))))

(defn leap-year-return-or
  [year value-leap-year value-not-leap-year]
  (cond (zero? (mod year 400)) value-leap-year
        (zero? (mod year 100)) value-not-leap-year
        (zero? (mod year 4)) value-leap-year
        :default value-not-leap-year))

(defn month-biggest-day
  [month year]
  (cond
    (= month 2) (leap-year-return-or year 29 28)
    (some #(= month %) '(1 3 5 7 9 10 12)) 31
    :else 30))

(def gen-month-and-year
  (gen/tuple
   (s/gen (s/and pos-int? (s/int-in 1 13)))
   (s/gen (s/and pos-int? (s/int-in 1 500)))))

(defn gen-days-smaller-than 
  [max]
  (s/gen (s/and pos-int? (s/int-in 1 (+ max 1)))))

(def gen-day-and-month
  (gen/bind
   gen-month-and-year
   (fn [month-and-year]
     (gen/tuple
      (gen/return (last month-and-year))
      (gen/return (first month-and-year))
      (gen-days-smaller-than 
       (apply month-biggest-day month-and-year))))))

(def generador-fecha
  (gen/fmap #(apply fecha %) gen-day-and-month))


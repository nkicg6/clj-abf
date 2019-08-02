(ns clj-abf.parse
  (:require [clojure.string :as str]
            [clojure.java.io :refer [file output-stream input-stream]])
  (:gen-class))

;; https://github.com/swharden/pyABF/tree/master/docs/advanced/abf-file-format
;; read the header!
;; https://stackoverflow.com/questions/23018870/how-to-read-a-whole-binary-file-nippy-into-byte-array-in-clojure#26372677
(def datapath
  "/Users/Nick/personal_projects/clj-abf/resources/data/14o08011_ic_pair.abf")

;; (defn slurp-bytes
;;   "read binary"
;;   ([path bytes-to-read]
;;    (let [f (java.io.File. path)
;;          ary (byte-array bytes-to-read)
;;          is (java.io.FileInputStream. f)]
;;      (.read is ary)
;;      (.close is)
;;      ary))
;;   ([path]
;;    (let [f (java.io.File. path)
;;          l (byte-array (.length f))
;;          stream (java.io.FileInputStream. f)]
;;      (.read stream l)
;;      (.close stream)
;;      l)))

;;(vec (slurp-bytes datapath 16))
;;(println (String. (slurp-bytes datapath 4)))

;; (defn seek-in-file
;;   [path seek-to]
;;   (with-open [f (java.io.FileInputStream. path)]
;;     (let [ba  (byte-array 1)]
;;       (.skip f seek-to)
;;       (.read f ba)
;;       (println (vec ba))
;;       (.skip f seek-to)
;;       (.read f ba)
;;       (println (vec ba)))    ))

;; (defn- open-stream
;;   [path]
;;   (let [stream (java.io.FileInputStream. path)]
;;     stream))


;; (defn seek-in-file
;;   [f seek-to bytes]
;;   (let [ba  (byte-array bytes)]
;;     (.skip f seek-to)
;;     (.read f ba)
;;     ba))

;; from stack overflow
;; (defn slurp-bytes
;;   "Slurp the bytes from a slurpable thing"
;;   [x]
;;   (with-open [out (java.io.ByteArrayOutputStream.)]
;;     (clojure.java.io/copy (clojure.java.io/input-stream x) out)
;;     (.toByteArray out)))

(def filebytes (slurp-bytes datapath))

(defn bytes? [x]
  (if (nil? x)
    false
    (= (Class/forName "[B")
       (.getClass x))))

(bytes? filebytes)

;; (apply str (map char (take 4 filebytes))) ;; can use take to get a set. 

(defn parse-header
  [path]
  (let [stream (open-stream path)]
    {:abf-version (String. (seek-in-file stream 0 4))
     :sweeps (vec (seek-in-file stream 8 1))}))

(vec (seek-in-file (open-stream datapath) 8 4))

(parse-header datapath)


(seek-in-file datapath 1)
(vec (seek-in-file datapath 1))
(vec (seek-in-file datapath 5))

(ns osw-lib-clj.atom
  (:import org.onesocialweb.model.atom.DefaultAtomFactory))

(defn entry
  [title]
  (let [e (.entry (DefaultAtomFactory.))]
    (.setTitle e title)
    e))

(ns osw-lib-clj.activity
  (:import org.onesocialweb.model.atom.DefaultAtomFactory
           org.onesocialweb.model.activity.DefaultActivityFactory
           org.onesocialweb.model.activity.ActivityVerb
           org.onesocialweb.model.activity.ActivityObject))

(defn verb
  []
  (let [v (.verb (DefaultActivityFactory.))]
    (.setValue v ActivityVerb/POST)
    v))

(defn object
  [content-value]
  (let [o (.object (DefaultActivityFactory.))]
    (.setType o (ActivityObject/STATUS_UPDATE))
    (if content-value
      (let [content (.content (DefaultAtomFactory.))]
        (.setValue content content-value)
        (.addContent o content)))
    o))

(defn entry
  [title]
  (let [e (.entry (DefaultActivityFactory.))]
    (.setTitle e title)
    e))

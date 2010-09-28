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

(comment
  (construct-object
   {:type :comment
    :content "This is a sample comment"
    :authors [{:email "duck@mycyclopedia.net"
               :name "Daniel E. Renfer"
               :uri "http://kronkltd.net/"}]
    :categories [{:label "Test"
                  :scheme "http://example.com/tags/"
                  :term "test"}]
    :parent {:id ""
             :jid "test@mycyclopedia.net"}
    :recipients [{:href ""
                  :ref ""
                  :type ""
                  :source ""}]}))

(defn construct-object
  [options]
  (let [object (.object (DefaultActivityFactory.))]
    (.setType
     object
     (cond
      (= (:type options) :status) ActivityObject/STATUS_UPDATE
      (= (:type options) :note)    ActivityObject/NOTE
      (= (:type options) :picture) ActivityObject/PICTURE
      (= (:type options) :comment) ActivityObject/COMMENT
      (= (:type options) :link)    ActivityObject/LINK
      (= (:type options) :video)   ActivityObject/VIDEO
      :else ActivityObject/NOTE))
    
    (if-let [content-value (:content options)]
      (let [content (.content (DefaultAtomFactory.))]
        (.setValue content content-value)
        (.addContent object content)))
    object))

(defn entry
  [title]
  (let [e (.entry (DefaultActivityFactory.))]
    (.setTitle e title)
    e))

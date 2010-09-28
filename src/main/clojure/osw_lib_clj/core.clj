(ns osw-lib-clj.core
  (:import org.onesocialweb.smack.OswServiceFactoryImp
           org.onesocialweb.client.exception.RequestException))

(def *default-resource* "osw-lib-clj")
(def *default-port* 5222)
(def *service-factory* (OswServiceFactoryImp.))
(def *user* (ref {}))
(def *service* nil)

(defn resource
  []
  (str *default-resource* "-"
       (rand-int 999999999)))

(defn create-service
  "Creates a new OSW service using the currently bound service factory"
  []
  (.createService *service-factory*))

(defmacro with-service
  [service# & forms]
  `(binding [osw-lib-clj.core/*service* ~service#]
     ~@forms))

(defn connect!
  [hostname]
  (.connect *service*
            hostname
            *default-port*
            {}))

(defn disconnect!
  "Disconnects the currently bound service from it's server"
  []
  (.disconnect *service*))

(defn login!
  ([username password]
     (login! username password (resource)))
  ([username password resource]
     (.login *service*
             username password resource)))

(defn add-connection-state-listener!
  [listener]
  (.addConnectionStateListener *service* listener))

(defn remove-connection-state-listener!
  [listener]
  (.removeConnectionStateListener *service* listener))

(defn post!
  [activity]
  (.postActivity *service* activity))

(defn add-subscription!
  [jid]
  (.subscribe *service* jid))

(defn remove-subscription!
  [jid]
  (.unsubscribe *service* jid))

(defn delete-activity!
  [id]
  (.deleteActivity *service* id))

(defn authenticated?
  []
  (.isAuthenticated *service*))

(defn connected?
  []
  (.isConnected *service*))

(defn authenticated-user
  []
  (.getUser *service*))

(defn activities
  [jid]
  (.getActivities *service* jid))

(defn subscribers
  [jid]
  (try
   (.getSubscribers *service* jid)
   (catch RequestException e nil)
   (catch NullPointerException e nil)))

(defn subscriptions
  [jid]
  (try
   (.getSubscriptions *service* jid)
   (catch RequestException e nil)
   (catch NullPointerException e nil)))

(def *profiles* (ref {}))

(defn profile
  [jid]
  (if-let [profile (@*profiles* jid)]
    profile
    (dosync
     (try
      (if-let [found-profile (.getProfile *service* jid)]
        (do
          (alter *profiles* assoc jid found-profile)
          found-profile))
      (catch RequestException e nil)))))

(defn inbox
  []
  (.getInbox *service*))

(defn relations
  [jid]
  (.getRelations *service* jid))

(defn inbox-entries
  []
  (map
   bean
   (.getEntries (inbox))))

(defn register!
  [username password name email]
  (.register *service* username password name email))

(defn replies
  [entry]
  (try
   (.getReplies *service* entry)
   (catch NullPointerException e nil)
   (catch RequestException e nil)))

(defn roster
  []
  (.getRoster
   (.getConnection *service*)))

(defn groups
  [roster]
  (.getGroups roster))

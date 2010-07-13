(ns osw-lib-clj.core
  (:import org.onesocialweb.smack.OswServiceFactoryImp))

(def *default-resource* "osw-lib-clj")
(def *default-port* 5266)

(def *service*
     (.createService (OswServiceFactoryImp.)))

(defn connect!
  ([server]
     (connect! server *default-port* {}))
  ([server port]
     (connect! server port {}))
  ([server port parameters]
     (.connect *service* server port parameters)))

(defmacro with-connection
  [{host :host,
    user :user,
    password :password,
    port :port} & body]
  `(do
     (if (and (connect! ~host
                        (or ~port *default-port*))
              (login! ~user ~password))
       (try
        ~@body
        (finally
         (.disconnect *service*)))
       (println "Could not connect"))))

(defn login!
  ([username password]
     (login! username password *default-resource*))
  ([username password resource]
     (.login *service* username password resource)))

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
  (.getSubscribers *service* jid))

(defn subscriptions
  [jid]
  (.getSubscriptions *service* jid))

(defn profile
  [jid]
  (.getProfile *service* jid))

(defn inbox
  []
  (.getInbox *service*))

(defn inbox-entries
  []
  (map
   bean
   (.getEntries (inbox))))


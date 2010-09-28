(ns osw-lib-clj.acl
  (:import org.onesocialweb.model.acl.AclSubject
           org.onesocialweb.model.acl.AclAction
           org.onesocialweb.model.acl.DefaultAclRule
           org.onesocialweb.model.acl.DefaultAclSubject
           org.onesocialweb.model.acl.DefaultAclAction))

(defn subject
  [key]
  (let [s (DefaultAclSubject.)]
    (if (= key :everyone)
      (.setType s AclSubject/EVERYONE))
    (if (= key :person)
      (.setType s AclSubject/PERSON))
    s))

(defn action
  [permission name]
  (let [a (DefaultAclAction.)]
    (if (= permission :grant)
      (.setPermission a AclAction/PERMISSION_GRANT))
    (if (= permission :deny)
      (.setPermission a AclAction/PERMISSION_DENY))
    (if (= name :view)
      (.setName a AclAction/ACTION_VIEW))
    a))

(defn rule
  [& options]
  (let [option-map (apply hash-map options)
        r (DefaultAclRule.)]
    (doseq [action (:actions option-map)]
      (.addAction r action))
    (doseq [subject (:subjects option-map)]
      (.addSubject r subject))
    r))


(defn public-rule
  []
  (rule :subjects [(subject :everyone)]
        :actions [(action :grant :view)]))

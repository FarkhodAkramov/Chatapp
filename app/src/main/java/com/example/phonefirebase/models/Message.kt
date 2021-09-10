package uz.mobiler.firebaseauthg18.models

class Message {

    var message: String? = null
    var fromUid: String? = null
    var toUid: String? = null
    var date: String? = null

    constructor(message: String?, fromUid: String?, toUid: String?, date: String?) {
        this.message = message
        this.fromUid = fromUid
        this.toUid = toUid
        this.date = date
    }

    constructor()


}
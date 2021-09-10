package uz.mobiler.firebaseauthg18.models

import java.io.Serializable

class User : Serializable {

    var name: String? = null
    var photoUrl: String? = null
    var gmail: String? = null
    var uid: String? = null
    var online:Boolean?=false



    constructor()
    constructor(name: String?, photoUrl: String?, gmail: String?, uid: String?, online: Boolean?) {
        this.name = name
        this.photoUrl = photoUrl
        this.gmail = gmail
        this.uid = uid
        this.online = online
    }


}

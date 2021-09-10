package com.example.phonefirebase.models

class GroupMessage {

    var message: String? = null
    var fromUid: String? = null
    var time: String? = null
    var isRead: Boolean = false
    var key: String? = null

    constructor(
        message: String?,
        fromUid: String?,
        time: String?,
        isRead: Boolean,
        key: String?
    ) {
        this.message = message
        this.fromUid = fromUid
        this.time = time
        this.isRead = isRead
        this.key = key
    }


    constructor()


}
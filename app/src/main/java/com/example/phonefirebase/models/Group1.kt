package com.example.phonefirebase.models

import java.io.Serializable

class Group1 : Serializable {
    var name: String? = null
    var title: String? = null

    constructor(name: String?, title: String?) {
        this.name = name
        this.title = title
    }

    constructor()

}
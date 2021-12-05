package com.yashkasera.aphrodite.model

/**
 * @author yashkasera
 * Created 04/12/21 at 10:11 PM
 */

class Message {
    var text: String? = null
    var name: String? = null

    constructor()
    constructor(text: String?, name: String?) {
        this.text = text
        this.name = name
    }
}

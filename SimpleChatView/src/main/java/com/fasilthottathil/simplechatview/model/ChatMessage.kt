package com.fasilthottathil.simplechatview.model

data class ChatMessage(
    var id: String = System.currentTimeMillis().toString(),
    var message: String = "",
    var username: String = "",
    var profile_url: String = "",
    var is_from_me: Boolean = true,
    var timestamp: Long = System.currentTimeMillis(),
    var message_type: Int = 0,
    var view_type: Int? = 0
)
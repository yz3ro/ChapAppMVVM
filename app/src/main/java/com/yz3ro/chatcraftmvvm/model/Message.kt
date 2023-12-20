package com.yz3ro.chatcraftmvvm.model

data class Message (
    var senderId: String? = null,
    var receiverId: String? = null,
    var text: String? = null,
    var timestamp: Any? = null
)
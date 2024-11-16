package com.anshuman.uniroomie.Modles

data class Message(
    val messageId: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val message: String = "",
    val timestamp: Long = 0L,
    val senderName: String = "",
    val receiverName: String = ""
)
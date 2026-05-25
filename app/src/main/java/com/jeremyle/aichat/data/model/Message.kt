package com.jeremyle.aichat.data.model

import java.util.UUID

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val role: MessageRole,
)

enum class MessageRole {
    USER, ASSISTANT
}
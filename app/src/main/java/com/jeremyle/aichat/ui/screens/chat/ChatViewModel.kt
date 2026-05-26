package com.jeremyle.aichat.ui.screens.chat

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ai.type.FirebaseAIException
import com.jeremyle.aichat.R
import com.jeremyle.aichat.data.ai.AIModelProvider
import com.jeremyle.aichat.data.model.Message
import com.jeremyle.aichat.data.model.MessageRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    var messages by mutableStateOf(listOf<Message>())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var thinkingText by mutableStateOf<String?>(null)

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        // add user message immediately
        messages = listOf(
            Message(content = content, role = MessageRole.USER)
        ) + messages

        // call Gemini
        viewModelScope.launch {
            isLoading = true
            val assistantMessage = Message(content = "", role = MessageRole.ASSISTANT)
            messages = listOf(assistantMessage) + messages

            try {
                if (shouldUseStreaming()) {
                    generateContentStreamAndUpdate(content)
                } else {
                    generateContentAndUpdate(content)
                }
            } catch (e: FirebaseAIException) {
                val error = messages.first().copy(
                    content = getString(R.string.error_message, e.message.orEmpty())
                )
                messages = listOf(error) + messages.drop(1)
            } finally {
                isLoading = false
            }
        }
    }

    private fun getString(resId: Int, message: String?): String {
        return getApplication<Application>().getString(resId, message)
    }

    private fun shouldEnableTypeWriterEffect(): Boolean {
        return false
    }

    private fun shouldUseStreaming(): Boolean {
        return false
    }

    private suspend fun generateContentStreamAndUpdate(content: String) {
        val stream = AIModelProvider.chatModel.generateContentStream(content)

        stream.collect { chunk ->
            // capture thinking separately
            chunk.thoughtSummary?.let { thought ->
                thinkingText = thought
                println("Thinking: $thought")
            }
            chunk.text?.let { token ->
                val updated = messages.first().copy(
                    content = messages.first().content + token
                )
                messages = listOf(updated) + messages.drop(1)
            }
        }
    }

    private suspend fun generateContentAndUpdate(content: String) {
        val response = AIModelProvider.chatModel.generateContent(content)

        response.thoughtSummary?.let { thought ->
            thinkingText = thought.take(80)
            println("Thinking: $thought")
            delay(1000L)
        }

        response.text?.let { token ->
            val updated = messages.first().copy(
                content = messages.first().content + token
            )
            messages = listOf(updated) + messages.drop(1)
        }
    }
}
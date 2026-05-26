package com.jeremyle.aichat.ui.screens.chat

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.FirebaseAIException
import com.google.firebase.ai.type.GenerativeBackend
import com.jeremyle.aichat.R
import com.jeremyle.aichat.data.model.Message
import com.jeremyle.aichat.data.model.MessageRole
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    var messages by mutableStateOf(listOf<Message>())
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val model =
        Firebase.ai(backend = GenerativeBackend.googleAI()).generativeModel("gemini-2.5-flash")

    fun sendMessage(content: String) {
        if (content.isBlank()) return

        // add user message immediately
        messages = listOf(
            Message(content = content, role = MessageRole.USER)
        ) + messages

        // call Gemini
        viewModelScope.launch {
            isLoading = true
            if (shouldEnableTypeWriterEffect()) {
                val assistantMessage = Message(content = "", role = MessageRole.ASSISTANT)
                messages = listOf(assistantMessage) + messages

                try {
                    val response = model.generateContent(content)
                    val fullText = response.text ?: return@launch

                    // reveal characters one by one
                    fullText.forEachIndexed { index, _ ->
                        val revealed = fullText.substring(0, index + 1)
                        val updated = messages.first().copy(content = revealed)
                        messages = listOf(updated) + messages.drop(1)
                        delay(1L)
                    }
                } catch (e: FirebaseAIException) {
                    val error = messages.first().copy(
                        content = getString(R.string.error_message, e.message.orEmpty())
                    )
                    messages = listOf(error) + messages
                } finally {
                    isLoading = false
                }
            } else {
                try {
                    val response = model.generateContent(content)
                    response.text?.let { reply ->
                        messages = listOf(
                            Message(content = reply, role = MessageRole.ASSISTANT)
                        ) + messages
                    }
                } catch (e: FirebaseAIException) {
                    val error = messages.first().copy(
                        content = getString(R.string.error_message, e.message.orEmpty())
                    )
                    messages = listOf(error) + messages
                } finally {
                    isLoading = false
                }
            }
        }
    }

    private fun getString(resId: Int, message: String?): String {
        return getApplication<Application>().getString(resId, message)
    }

    private fun shouldEnableTypeWriterEffect(): Boolean {
        return false
    }
}
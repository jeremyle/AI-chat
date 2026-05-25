package com.jeremyle.aichat.ui.screens.chat

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.FirebaseAIException
import com.google.firebase.ai.type.GenerativeBackend
import com.jeremyle.aichat.R
import com.jeremyle.aichat.data.model.Message
import com.jeremyle.aichat.data.model.MessageRole
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
            try {
                val response = model.generateContent(content)
                response.text?.let { reply ->
                    messages = listOf(
                        Message(content = reply, role = MessageRole.ASSISTANT)
                    ) + messages
                }
            } catch (e: FirebaseAIException) {
                messages = listOf(
                    Message(content = getApplication<Application>().getString(R.string.error_message, e.message), role = MessageRole.ASSISTANT)
                )
            } finally {
                isLoading = false
            }
        }
    }
}
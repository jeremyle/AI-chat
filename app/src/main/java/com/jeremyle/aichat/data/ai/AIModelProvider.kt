package com.jeremyle.aichat.data.ai

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.ThinkingLevel
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import com.google.firebase.ai.type.thinkingConfig

object AIModelProvider {

    private const val MODEL_NAME = "gemini-3.5-flash"

    val chatModel by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(
                modelName = MODEL_NAME,
                generationConfig = generationConfig {
                    thinkingConfig = thinkingConfig {
                        includeThoughts = true
                        thinkingLevel = ThinkingLevel.HIGH
                    }
                },
                systemInstruction = content {
                    text("You are a helpful AI assistant. Answer question clearly and concisely.")
                }
            )
    }
}
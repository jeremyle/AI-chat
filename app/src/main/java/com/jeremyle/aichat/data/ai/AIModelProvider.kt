package com.jeremyle.aichat.data.ai

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.ThinkingLevel
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import com.google.firebase.ai.type.thinkingConfig

object AIModelProvider {

    private const val MODEL_NAME = "gemini-3.1-flash-lite"

    val chatModel by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(
                modelName = MODEL_NAME,
                generationConfig = generationConfig {
                    thinkingConfig {
                        includeThoughts = true
                        thinkingLevel = ThinkingLevel.HIGH
                    }
                    temperature = 0.7f
                },
                systemInstruction = content {
                    text("You are a helpful AI assistant. Answer question clearly and concisely.")
                }
            )
    }
}
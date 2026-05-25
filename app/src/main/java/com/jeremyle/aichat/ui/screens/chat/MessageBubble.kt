package com.jeremyle.aichat.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jeremyle.aichat.data.model.Message
import com.jeremyle.aichat.data.model.MessageRole
import com.jeremyle.aichat.ui.theme.Spacing
import com.mikepenz.markdown.m3.Markdown

@Composable
fun MessageBubble(message: Message) {
    when (message.role) {
        MessageRole.USER -> UserMessageBubble(message)
        MessageRole.ASSISTANT -> AssistantMessageBubble(message)
    }
}

@Composable
private fun UserMessageBubble(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(start = Spacing.xxl, end = Spacing.lg, top = Spacing.md, bottom = Spacing.md),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp,
                        bottomStart = 32.dp,
                        bottomEnd = 32.dp,
                    )
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(Spacing.md)
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun AssistantMessageBubble(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md)
    ) {
        Markdown(message.content)
    }
}
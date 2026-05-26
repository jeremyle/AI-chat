package com.jeremyle.aichat.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.jeremyle.aichat.R
import com.jeremyle.aichat.ui.theme.BottomGradient
import com.jeremyle.aichat.ui.theme.Spacing
import com.jeremyle.aichat.utils.isKeyboardVisible

// An input bar that supports loading indicator at the top of the input box
@Composable
fun InputBar(
    onSendClick: (String) -> Unit,
    onSizeChanged: (IntSize) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    var text by remember { mutableStateOf("") }

    LifecycleResumeEffect(Unit) {
        focusRequester.requestFocus()
        onPauseOrDispose { }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .imePadding()
            .onSizeChanged { size -> onSizeChanged(size) }
    ) {
        // gradient blur — drawn first so it's underneath
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.BottomCenter)
                .background(BottomGradient)
        )

        val bottomPadding by animateDpAsState(
            targetValue = if (isKeyboardVisible()) Spacing.md else Spacing.xl,
            label = "bottomPadding"
        )
        // chat input stuck to the bottom of the screen
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Transparent)
                .padding(horizontal = Spacing.lg)
                .padding(bottom = bottomPadding)
        ) {
            // loading indicator — appears at bottom because reverseLayout = true
            if (isLoading) {
                Text(
                    text = stringResource(R.string.thinking_message),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(Spacing.lg)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(Spacing.lg)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .focusRequester(focusRequester),
                    textStyle = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                text = stringResource(R.string.message_hint),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        innerTextField()
                    }
                )

                Spacer(modifier = Modifier.width(Spacing.lg))

                IconButton(
                    onClick = {
                        onSendClick(text)
                        text = ""
                    },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
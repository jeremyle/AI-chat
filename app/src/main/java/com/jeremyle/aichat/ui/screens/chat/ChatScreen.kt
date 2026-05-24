package com.jeremyle.aichat.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.jeremyle.aichat.R
import com.jeremyle.aichat.ui.components.TopBar
import com.jeremyle.aichat.ui.theme.GradientBottom
import com.jeremyle.aichat.ui.theme.GradientTop
import com.jeremyle.aichat.ui.theme.Spacing

@Composable
fun ChatScreen() {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            GradientTop,
            GradientBottom, // light blue at the bottom
        )
    )

    LifecycleResumeEffect(Unit) {
        focusRequester.requestFocus()
        onPauseOrDispose { }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.app_name),
                onMenuClick = {})
        }
    ) { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBackground)
        ) {

            // chat input stuck to the bottom of the screen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Transparent)
                    .imePadding()
                    .padding(horizontal = Spacing.lg, vertical = Spacing.md)
            ) {
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
                        onClick = {},
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                    }
                }
            }
        }
    }
}
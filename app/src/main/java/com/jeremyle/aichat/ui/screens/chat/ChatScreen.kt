package com.jeremyle.aichat.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jeremyle.aichat.R
import com.jeremyle.aichat.data.model.Message
import com.jeremyle.aichat.ui.components.InputBar
import com.jeremyle.aichat.ui.components.TopBar
import com.jeremyle.aichat.ui.theme.BackgroundGradient
import com.jeremyle.aichat.ui.theme.Spacing

@Composable
fun ChatScreen(viewModel: ChatViewModel = viewModel()) {
    val messages = viewModel.messages
    val isLoading = viewModel.isLoading
    var inputBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val imeInsets = WindowInsets.ime
    val totalBottomPadding by remember {
        derivedStateOf {
            inputBarHeight + with(density) { imeInsets.getBottom(density).toDp() }
        }
    }
    var topBarHeight by remember { mutableStateOf(0.dp) }

    val onSend = { text: String ->
        if (text.isNotBlank()) {
            viewModel.sendMessage(text)
        }
    }

    scrollToNewestMessage(messages)

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.app_name),
                onMenuClick = {},
                modifier = Modifier.onSizeChanged { size ->
                    topBarHeight = with(density) { size.height.toDp() }
                }
            )
        }
    ) { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
        ) {

            // scrollable messages list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = totalBottomPadding),
                reverseLayout = true, // newest message at the bottom
            ) {

                items(messages) { message ->
                    MessageBubble(message)
                }

                // Spacer for the first item to be below the top bar
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(topBarHeight)
                    )
                }
            }

            // Input bar at the bottom of the screen
            InputBar(
                onSendClick = onSend,
                onSizeChanged = { size ->
                    inputBarHeight = with(density) { size.height.toDp() }
                },
                isLoading = isLoading,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
private fun scrollToNewestMessage(messages: List<Message>) {
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }
}
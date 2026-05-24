package com.jeremyle.aichat.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.jeremyle.aichat.R
import com.jeremyle.aichat.ui.components.TopBar

@Composable
fun ChatScreen() {
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
                .background(Color.Red)
        ) {
        }
    }
}
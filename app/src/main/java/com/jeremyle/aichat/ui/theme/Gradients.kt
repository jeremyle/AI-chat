package com.jeremyle.aichat.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val BackgroundGradient = Brush.verticalGradient(
    colors = listOf(GradientTop, GradientBottom)
)

val BottomGradient = Brush.verticalGradient(
    colors = listOf(
        Color.Transparent,
        GradientBottom.copy(alpha = 0.3f),
        GradientBottom.copy(alpha = 0.7f),
        GradientBottom.copy(alpha = 0.9f),
    )
)

val TopGradient = Brush.verticalGradient(
    colors = listOf(
        GradientBottom.copy(alpha = 0.9f),
        GradientBottom.copy(alpha = 0.7f),
        GradientBottom.copy(alpha = 0.3f),
        Color.Transparent,
    )
)
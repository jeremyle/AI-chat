package com.jeremyle.aichat.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
}
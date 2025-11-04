package com.dalingge.coinvista.navigation.models

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/11/4  13:51
 */
sealed class MainScreen(val route: String) {

    object Splash : MainScreen("splash")

    object MainGraph : MainScreen("main") {

        data object Home : MainScreen("home")

        data object Market : MainScreen("market")

        data object Mine : MainScreen("mine")
    }
}
package com.dalingge.coinvista.main.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dalingge.coinvista.main.view.MainRoute
import com.dalingge.coinvista.main.view.SplashRoute
import com.dalingge.coinvista.navigation.routes.MainRoutes

/**
 *
 * @Description : 主模块导航图
 * @Author :丁博洋
 * @Time :2025/10/11  16:04
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
) {
    splashScreen(sharedTransitionScope)
    mainScreen(navController)
}


/**
 * 启动页面导航
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.splashScreen(sharedTransitionScope: SharedTransitionScope) {
    composable<MainRoutes.Splash> {
        SplashRoute(sharedTransitionScope, this@composable)
    }
}

/**
 * 注册主页面路由
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainScreen(navController: NavHostController) {
    composable<MainRoutes.Main> {
        MainRoute()
    }
}
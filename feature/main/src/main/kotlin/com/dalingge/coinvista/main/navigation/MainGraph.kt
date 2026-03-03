package com.dalingge.coinvista.main.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.dalingge.coinvista.core.navigation.routes.MainRoutes
import com.dalingge.coinvista.main.view.MainRoute
import com.dalingge.coinvista.main.view.SplashRoute

/**
 *
 * @Description : 主模块导航图
 * @Author :Dalingge
 * @Time :2025/10/11  16:04
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun EntryProviderScope<NavKey>.mainGraph(
    sharedTransitionScope: SharedTransitionScope,
) {
    splashScreen(sharedTransitionScope)
    mainScreen(sharedTransitionScope)
}


/**
 * 启动页面导航
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun EntryProviderScope<NavKey>.splashScreen(sharedTransitionScope: SharedTransitionScope) {
//    entry<MainRoutes.Splash> {
//        SplashRoute(
//            sharedTransitionScope = sharedTransitionScope,
//            animatedContentScope = LocalNavAnimatedContentScope.current
//        )
//    }
}

/**
 * 注册主页面路由
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun EntryProviderScope<NavKey>.mainScreen(sharedTransitionScope: SharedTransitionScope) {
//    entry<MainRoutes.Main> {
//        MainRoute(
//            sharedTransitionScope = sharedTransitionScope,
//            animatedContentScope = LocalNavAnimatedContentScope.current,
//        )
//    }
}
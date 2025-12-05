package com.dalingge.coinvista.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.dalingge.coinvista.common.view.WebRoute
import com.dalingge.coinvista.navigation.routes.CommonRoutes

/**
 * 通用模块导航图
 *
 * @Description : 通用模块下所有页面的导航
 * @Author :Dalingge
 * @Time :2025/10/14  14:59
 */
fun NavGraphBuilder.commonGraph(navController: NavHostController) {
    webScreen()
}


/**
 * 网页页面导航
 */
fun NavGraphBuilder.webScreen() {
    composable<CommonRoutes.Web> {
        WebRoute()
    }
}
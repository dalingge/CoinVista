package com.dalingge.coinvista.core.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 主模块路由
 */
object MainRoutes {

    /**
     * 启动页路由
     */
    @Serializable
    data object Splash : NavKey

    /**
     * 主框架路由
     *
     * 应用的主框架，包含底部导航栏
     */
    @Serializable
    data object Main : NavKey

    /**
     * 市场页面路由
     */
    @Serializable
    data object Market : NavKey
    /**
     * 首页路由
     */
    @Serializable
    data object New : NavKey
    /**
     * 投资组合页面路由
     */
    @Serializable
    data object Portfolio : NavKey

    /**
     * 我的页面路由
     */
    @Serializable
    data object Mine : NavKey
}

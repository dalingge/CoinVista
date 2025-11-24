package com.dalingge.coinvista.navigation.routes

import kotlinx.serialization.Serializable

/**
 * 主模块路由
 */
object MainRoutes {

    /**
     * 启动页路由
     */
    @Serializable
    data object Splash

    /**
     * 主框架路由
     *
     * 应用的主框架，包含底部导航栏
     */
    @Serializable
    data object Main

    /**
     * 首页路由
     *
     * 应用首页，展示推荐商品和营销活动
     */
    @Serializable
    data object Home

    /**
     * 行情页面路由
     *
     * 行情分类浏览页面
     */
    @Serializable
    data object Market

    /**
     * 我的页面路由
     *
     * 个人中心页面
     */
    @Serializable
    data object Mine
}

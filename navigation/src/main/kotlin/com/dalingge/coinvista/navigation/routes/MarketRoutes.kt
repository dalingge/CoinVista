package com.dalingge.coinvista.navigation.routes

import kotlinx.serialization.Serializable

/**
 *
 * @Description :市场行情模块路由
 * @Author : Dalingge
 * @Time :2025/12/8  11:02
 */
object MarketRoutes {

    /**
     * 搜索市场
     *
     */
    @Serializable
    data object Search


    /**
     * 数字货币详情
     *
     */
    @Serializable
    data object Details
}
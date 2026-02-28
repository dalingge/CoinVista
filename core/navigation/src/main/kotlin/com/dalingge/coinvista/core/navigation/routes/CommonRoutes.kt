package com.dalingge.coinvista.core.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 公共信息模块路由
 */
object CommonRoutes {
    /**
     * 关于我们路由
     *
     */
    @Serializable
    data object About : NavKey

    /**
     * WebView 页面路由
     *
     * @param url 要加载的URL
     * @param title 页面标题（可选）
     */
    @Serializable
    data class Web(
        val url: String,
        val title: String? = null
    ) : NavKey

    /**
     * 设置页面路由
     */
    @Serializable
    data object Settings : NavKey

    /**
     * 用户协议路由
     */
    @Serializable
    data object UserAgreement : NavKey

    /**
     * 隐私政策路由
     */
    @Serializable
    data object PrivacyPolicy : NavKey

    /**
     * 贡献者列表路由
     */
    @Serializable
    data object Contributors : NavKey
}

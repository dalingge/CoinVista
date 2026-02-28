package com.dalingge.coinvista.core.navigation.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * 认证模块路由
 */
object AuthRoutes {
    /**
     * 登录主页路由
     */
    @Serializable
    data object Login : NavKey

    /**
     * 账号密码登录路由
     */
    @Serializable
    data object AccountLogin : NavKey

    /**
     * 短信验证码登录路由
     */
    @Serializable
    data object SmsLogin : NavKey

    /**
     * 注册页面路由
     */
    @Serializable
    data object Register : NavKey

    /**
     * 找回密码路由
     */
    @Serializable
    data object ResetPassword : NavKey
}

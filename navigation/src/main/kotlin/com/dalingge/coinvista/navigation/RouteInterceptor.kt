package com.dalingge.coinvista.navigation

import com.dalingge.coinvista.navigation.routes.AuthRoutes
import com.dalingge.coinvista.navigation.routes.MainRoutes
import kotlin.reflect.KClass


/**
 * 路由拦截器
 * 负责管理需要登录的页面配置和路由拦截逻辑
 */
class RouteInterceptor {

    /**
     * 需要登录的页面路由集合
     * 在这里配置所有需要登录才能访问的页面
     */
    private val loginRequiredRouteTypes = setOf(
        // 用户模块 - 需要登录的页面
        AuthRoutes.AccountLogin::class,
        AuthRoutes.SmsLogin::class
    )

    /**
     * 检查指定路由是否需要登录
     *
     * @param route 要检查的路由
     * @return true表示需要登录，false表示不需要登录
     */
    fun requiresLogin(route: Any): Boolean {
        // 获取路由对象的类型
        val routeClass = route::class

        // 检查是否在需要登录的路由类型集合中
        return loginRequiredRouteTypes.contains(routeClass)
    }

    /**
     * 获取登录页面路由
     *
     * @return 登录页面的路由
     */
    fun getLoginRoute(): Any {
        return AuthRoutes.Login
    }

    /**
     * 添加需要登录的路由类型
     *
     * @param routeClass 需要登录的路由类型
     */
    fun addLoginRequiredRoute(routeClass: KClass<*>) {
        (loginRequiredRouteTypes as MutableSet).add(routeClass)
    }

    /**
     * 移除需要登录的路由类型
     *
     * @param routeClass 不再需要登录的路由类型
     */
    fun removeLoginRequiredRoute(routeClass: KClass<*>) {
        (loginRequiredRouteTypes as MutableSet).remove(routeClass)
    }

    /**
     * 获取所有需要登录的路由类型
     *
     * @return 需要登录的路由类型集合
     */
    fun getLoginRequiredRoutes(): Set<KClass<*>> {
        return loginRequiredRouteTypes.toSet()
    }
}
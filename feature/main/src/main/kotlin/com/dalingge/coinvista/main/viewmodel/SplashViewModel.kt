package com.dalingge.coinvista.main.viewmodel

import com.dalingge.coinvista.core.common.base.viewmodel.BaseViewModel
import com.dalingge.coinvista.main.view.MainRouteDestination
import com.dalingge.nav.runtime.NavCenter

/**
 *
 * @Description : 启动页 ViewModel
 * @Author :Dalingge
 * @Time :2025/10/14  14:22
 */
class SplashViewModel : BaseViewModel() {

    /**
     * 检查是否已显示过引导页，并根据结果跳转到相应页面
     */
//    fun checkGuideStatusAndNavigate() {
//        if (isGuideShown()) {
//            // 已显示过引导页，直接跳转到主页面
//            toMainPage()
//        } else {
//            // 未显示过引导页，跳转到引导页
//            toGuidePage()
//        }
//    }

    /**
     * 检查是否已显示过引导页
     *
     * @return true表示已显示过，false表示未显示过
     */
//    private fun isGuideShown(): Boolean {
//        return MMKVUtils.getBoolean(KEY_GUIDE_SHOWN, false)
//    }

    /**
     * 跳转到引导页并关闭当前启动页
     */
//    private fun toGuidePage() {
//        toPageAndCloseCurrent(LaunchRoutes.GUIDE, LaunchRoutes.SPLASH)
//    }

    /**
     * 跳转到主页并关闭当前启动页
     */
    fun toMainPage() {

        NavCenter.navigate(MainRouteDestination()){
            clearTask = true
        }
//        navigateAndCloseCurrent(
//            route = MainRoutes.Main,
//            currentRoute = MainRoutes.Splash
//        )
    }
}
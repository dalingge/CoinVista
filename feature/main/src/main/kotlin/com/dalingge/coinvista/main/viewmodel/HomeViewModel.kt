package com.dalingge.coinvista.main.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import com.dalingge.coinvista.core.common.base.viewmodel.BaseViewModel
import com.dalingge.coinvista.core.data.state.AppState
import com.dalingge.coinvista.navigation.AppNavigator

class HomeViewModel(
    navigator: AppNavigator,
    appState: AppState
) : BaseViewModel(navigator, appState) ,DefaultLifecycleObserver{



    /**
     * 跳转到搜索页面
     */
    fun toSearch() {

    }

    /**
     * 跳转到 GitHub 页面
     */
    fun toGitHubPage() {
        val url = "https://github.com/Joker-x-dev/CoolMallKotlin"
        val title = "GitHub"
//        super.toPage(
//            "${CommonRoutes.WEB}?url=${
//                java.net.URLEncoder.encode(
//                    url,
//                    "UTF-8"
//                )
//            }&title=${java.net.URLEncoder.encode(title, "UTF-8")}"
//        )
    }
}
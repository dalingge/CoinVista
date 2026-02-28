package com.dalingge.coinvista.market.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.dalingge.coinvista.core.navigation.routes.MarketRoutes
import com.dalingge.coinvista.market.view.SearchRoute

/**
 * 通用模块导航图
 *
 * @Description : 通用模块下所有页面的导航
 * @Author :Dalingge
 * @Time :2025/10/14  14:59
 */
@OptIn(ExperimentalSharedTransitionApi::class)
fun EntryProviderScope<NavKey>.marketGraph() {

    entry<MarketRoutes.Search> {
        SearchRoute()
    }
}

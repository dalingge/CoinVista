package com.dalingge.coinvista.main.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.dalingge.coinvista.core.navigation.routes.MainRoutes
import com.dalingge.coinvista.feature.main.R

enum class TopLevelDestination(
    @param:StringRes val titleTextId: Int,
    @param:DrawableRes val iconResId: Int,
    val route: NavKey,
) {
    MARKETS(
        titleTextId = R.string.main_tab_markets,
        iconResId = R.drawable.ic_tab_markets,
        route = MainRoutes.Market
    ),
    NEWS(
        titleTextId = R.string.main_tab_news,
        iconResId = R.drawable.ic_tab_news,
        route = MainRoutes.New
    ),
    PORTFOLIO(
        titleTextId = R.string.main_tab_portfolio,
        iconResId = R.drawable.ic_tab_portfolio,
        route = MainRoutes.Portfolio
    ),
    MINE(
        titleTextId = R.string.main_tab_mine,
        iconResId = R.drawable.ic_tab_mine,
        route = MainRoutes.Mine
    )
}
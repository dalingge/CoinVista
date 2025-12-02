package com.dalingge.coinvista.main.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dalingge.coinvista.feature.main.R
import com.dalingge.coinvista.navigation.routes.MainRoutes

enum class TopLevelDestination(
    @param:StringRes val titleTextId: Int,
    @param:DrawableRes val iconResId: Int,
    val route: Any,
) {
    HOME(
        titleTextId = R.string.main_tab_home,
        iconResId = R.drawable.ic_tab_home,
        route = MainRoutes.Home
    ),
    MARKET(
        titleTextId = R.string.main_tab_market,
        iconResId = R.drawable.ic_tab_market,
        route = MainRoutes.Market
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
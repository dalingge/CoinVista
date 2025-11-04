package com.dalingge.coinvista.main.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dalingge.coinvista.feature.main.R
import com.dalingge.coinvista.navigation.models.MainScreen

enum class TopLevelDestination(
    @param:StringRes val titleTextId: Int,
    @param:DrawableRes val iconResId: Int,
    val screen: MainScreen,
) {
    HOME(
        titleTextId = R.string.main_tab_home,
        iconResId = R.drawable.ic_tab_home,
        screen = MainScreen.MainGraph.Home
    ),
    MARKET(
        titleTextId = R.string.main_tab_market,
        iconResId = R.drawable.ic_tab_market,
        screen = MainScreen.MainGraph.Market
    ),
    MINE(
        titleTextId = R.string.main_tab_mine,
        iconResId = R.drawable.ic_tab_mine,
        screen =MainScreen.MainGraph.Mine
    )
}
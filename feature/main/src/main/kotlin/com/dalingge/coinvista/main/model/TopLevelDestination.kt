package com.dalingge.coinvista.main.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dalingge.coinvista.feature.main.R

enum class TopLevelDestination(
    @param:StringRes val titleTextId: Int,
    @param:DrawableRes val iconResId: Int,
) {
    MARKETS(
        titleTextId = R.string.main_tab_markets,
        iconResId = R.drawable.ic_tab_markets,
    ),
    NEWS(
        titleTextId = R.string.main_tab_news,
        iconResId = R.drawable.ic_tab_news,
    ),
    PORTFOLIO(
        titleTextId = R.string.main_tab_portfolio,
        iconResId = R.drawable.ic_tab_portfolio,
    ),
    MINE(
        titleTextId = R.string.main_tab_mine,
        iconResId = R.drawable.ic_tab_mine,
    )
}
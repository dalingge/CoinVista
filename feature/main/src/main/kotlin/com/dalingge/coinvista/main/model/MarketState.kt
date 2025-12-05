package com.dalingge.coinvista.main.model

sealed class MarketTab(val title: String, val index: Int) {
    // 1. 通用列表类型 (排行榜、热门、涨幅、跌幅)
    // 它们共享一套 UI 和数据结构，只是请求参数不同
    data class CryptoRank(
        val tabIndex: Int,
        val rankType: RankType // 枚举：HOT, GAINERS, LOSERS, MARKET_CAP
    ) : MarketTab(title = rankType.uiName, index = tabIndex)

    // 2. 交易所类型 (完全不同的接口和 UI)
    data class Exchanges(val tabIndex: Int) : MarketTab("交易所", tabIndex)

    // 3. NFT 类型 (完全不同的接口和 UI)
    data class Nft(val tabIndex: Int) : MarketTab("NFTs", tabIndex)

    // 4. 自选类型 (来自本地数据库，UI 可能类似 CryptoRank 但逻辑不同)
    data class Watchlist(val tabIndex: Int) : MarketTab("自选", tabIndex)

    // 5. 类别类型 (完全不同的接口和 UI)
    data class Categories(val tabIndex: Int) : MarketTab("类别", tabIndex)
}

// 辅助枚举，用于通用列表区分请求参数
enum class RankType(val uiName: String) {
    TOP("排行榜"),
    HOT("热门"),
    GAINERS("涨幅榜"),
    LOSERS("跌幅榜")
}
package com.dalingge.coinvista.main.model

import com.dalingge.coinvista.core.model.entity.MarketsCategories
import com.dalingge.coinvista.core.model.entity.MarketsCoins
import com.dalingge.coinvista.core.model.entity.NFTsTrending
import com.dalingge.coinvista.core.model.entity.TickersExchanges

/**
 *
 * @Description :定义 UI 状态的密封类，解决不同页面数据类型不同的问题
 * @Author : Dalingge
 * @Time :2025/12/5  11:44
 */
sealed interface MarketTabUiState {

    data object Loading : MarketTabUiState

    data object Empty : MarketTabUiState

    data class Error(val msg: String) : MarketTabUiState

    // 1. 通用币种列表数据
    data class CryptoList(val data: List<MarketsCoins>) : MarketTabUiState

    // 2. 交易所数据
    data class ExchangeList(val data: List<TickersExchanges>) : MarketTabUiState

    // 3. NFT数据
    data class NFTsList(val data: List<NFTsTrending>) : MarketTabUiState

    // 4. 类别数据
    data class CategoriesList(val data: List<MarketsCategories>) : MarketTabUiState
}
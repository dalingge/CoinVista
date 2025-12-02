package com.dalingge.coinvista.main.model

import com.dalingge.coinvista.core.common.base.state.LoadMoreState
import com.dalingge.coinvista.core.model.entity.MarketsCoins
import com.dalingge.coinvista.core.model.entity.NFTsTrending
import com.dalingge.coinvista.core.model.entity.TickersExchanges


/**
 * 订单标签页状态
 *
 * 用于封装每个标签页的状态数据和回调
 *
 * @param uiState UI网络状态
 * @param marketsCoins 订单列表
 * @param loadMoreState 加载更多状态
 * @param onRetry 重试回调
 * @param onLoadMore 加载更多回调
 * @param shouldTriggerLoadMore 是否应触发加载更多的判断函数
 * @author 丁博洋
 */
data class MarketTabState(
    val uiState: MarketTabUiState,
    val isRefreshing: Boolean,
    val loadMoreState: LoadMoreState,
    val onRetry: () -> Unit,
    val onRefresh: () -> Unit,
    val onLoadMore: () -> Unit,
    val shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    val enablePullToRefresh: () -> Boolean = { true }
)


// 定义 UI 状态的密封类，解决不同页面数据类型不同的问题
sealed interface MarketTabUiState {
    data object Loading : MarketTabUiState
    data object Empty : MarketTabUiState
    data class Error(val msg: String) : MarketTabUiState

    // 1. 通用币种列表数据
    data class CryptoList(val data: List<MarketsCoins>) : MarketTabUiState

    // 2. 交易所数据
    data class ExchangeList(val data: List<TickersExchanges>) : MarketTabUiState

    // 3. NFT 数据
    data class NftList(val data: List<NFTsTrending>) : MarketTabUiState
}


//  定义每个 Tab 的完整状态包裹器 (包含分页、刷新状态等)
data class TabViewState(
    val page: Int = 1,
    val isRefreshing: Boolean = false,
    val loadMoreState: LoadMoreState = LoadMoreState.PullToLoad,
    val uiState: MarketTabUiState = MarketTabUiState.Loading,
    val hasLoaded: Boolean = false // 标记是否初始化过
)

package com.dalingge.coinvista.main.model

import com.dalingge.coinvista.core.common.base.state.BaseNetWorkListUiState
import com.dalingge.coinvista.core.common.base.state.LoadMoreState
import com.dalingge.coinvista.core.model.entity.NewsItem

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/5  11:39
 */
data class NewsTabState(
    val uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
    val newsList: List<NewsItem> = emptyList(),
    val page: Int = 1,
    val hasLoaded: Boolean = false,
    val isRefreshing: Boolean = false,
    val loadMoreState: LoadMoreState = LoadMoreState.PullToLoad,
    val onRetry: () -> Unit = {},
    val onRefresh: () -> Unit = {},
    val onLoadMore: () -> Unit = {},
    val shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean = { _, _ -> false },
)

//  定义每个 Tab 的完整状态包裹器 (包含分页、刷新状态等)
//data class TabViewState(
//    val uiState: BaseNetWorkListUiState = BaseNetWorkListUiState.Loading,
//    val page: Int = 1,
//    val isRefreshing: Boolean = false,
//    val loadMoreState: LoadMoreState = LoadMoreState.PullToLoad,
//    val hasLoaded: Boolean = false // 标记是否初始化过
//)
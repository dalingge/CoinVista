package com.dalingge.coinvista.main.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.dalingge.coinvista.core.common.base.state.BaseNetWorkListUiState
import com.dalingge.coinvista.core.common.base.state.LoadMoreState
import com.dalingge.coinvista.core.common.base.viewmodel.BaseViewModel
import com.dalingge.coinvista.core.common.result.ResultHandler
import com.dalingge.coinvista.core.common.result.asResult
import com.dalingge.coinvista.core.data.repository.NewsRepository
import com.dalingge.coinvista.core.model.entity.NewsItem
import com.dalingge.coinvista.core.navigation.navigate
import com.dalingge.coinvista.core.navigation.routes.CommonRoutes
import com.dalingge.coinvista.main.model.NewsState
import com.dalingge.coinvista.main.model.NewsTabState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewViewModel(
    private val newsRepository: NewsRepository,
) : BaseViewModel(), DefaultLifecycleObserver {


    // 当前选中的标签索引
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    //是否正在进行标签切换动画
    private val _isAnimatingTabChange = MutableStateFlow(false)
    val isAnimatingTabChange: StateFlow<Boolean> = _isAnimatingTabChange.asStateFlow()

    // key: Tab 的 index, value: 该 Tab 的状态
    private val _tabUiStates = NewsState.entries.map { MutableStateFlow(NewsTabState()) }

    // 对外暴露获取特定 Tab 状态的方法
    val tabUiStates: List<StateFlow<NewsTabState>> = _tabUiStates.map { it.asStateFlow() }

    init {
        // 加载当前选中标签页的数据
        loadTabDataIfNeeded(_selectedTabIndex.value)
    }

    /**
     * 加载数据（如果未加载）
     */
    private fun loadTabDataIfNeeded(index: Int) {
        val currentState = _tabUiStates[index].value
        if (!currentState.hasLoaded) {
            loadListData(index)
        }
    }

    /**
     * 加载指定标签页的列表数据
     */
    private fun loadListData(index: Int) {
        val currentState = _tabUiStates[index].value
        val type = NewsState.entries[index].type

        // 更新 UI 为 Loading (仅首次)
        if (currentState.page == 1 && currentState.loadMoreState != LoadMoreState.Loading && !currentState.isRefreshing) {
            updateTabState(index) { it.copy(uiState = BaseNetWorkListUiState.Loading) }
        }

        ResultHandler.handleResult(
            scope = viewModelScope,
            flow = newsRepository.getNewsType(type, currentState.page).asResult(),
            onSuccessWithData = { processSuccess(index, it) },
            onError = { msg, _ -> handleNewsError(index, msg) }
        )
    }

    /**
     * 处理新闻列表请求成功
     */
    private fun processSuccess(
        index: Int,
        newItems: List<NewsItem>?,
    ) {
        val safeItems = newItems ?: emptyList()
        val hasNext = safeItems.isNotEmpty()

        updateTabState(index) { currentState ->
            // 1. 计算新的列表数据
            val newList = if (currentState.page == 1) {
                // 第一页：直接覆盖
                safeItems
            } else {
                // 加载更多：追加数据 (建议加上 distinctBy 防止 id 重复导致的列表跳动)
                (currentState.newsList + safeItems).distinctBy { it.id }
            }

            // 2. 决定 UI 状态
            val newUiState = if (newList.isEmpty()) {
                BaseNetWorkListUiState.Empty
            } else {
                BaseNetWorkListUiState.Success
            }

            // 3. 返回新状态
            currentState.copy(
                hasLoaded = true,
                isRefreshing = false,
                uiState = newUiState,
                newsList = newList, // 直接更新列表字段
                loadMoreState = if (hasNext) LoadMoreState.PullToLoad else LoadMoreState.NoMore
            )
        }
    }

    private fun handleNewsError(index: Int, msg: String) {
        updateTabState(index) { currentState ->
            val isFirstPage = currentState.page == 1

            // 如果是加载更多失败，计算回退后的页码
            val adjustedPage = if (!isFirstPage) {
                (currentState.page - 1).coerceAtLeast(1)
            } else {
                currentState.page
            }

            currentState.copy(
                isRefreshing = false,
                page = adjustedPage, // 更新页码
                uiState = if (isFirstPage) BaseNetWorkListUiState.Error else currentState.uiState,
                loadMoreState = if (isFirstPage) LoadMoreState.PullToLoad else LoadMoreState.Error
            )
        }
    }

    private fun updateTabState(index: Int, update: (NewsTabState) -> NewsTabState) {
        _tabUiStates[index].update(update)
    }

    /**
     * 更新选中的标签
     */
    fun updateSelectedTab(index: Int) {
        if (_selectedTabIndex.value != index) {
            _selectedTabIndex.value = index
            _isAnimatingTabChange.value = true

            // 当切换到新标签页时，检查并按需加载数据
            loadTabDataIfNeeded(index)
        }
    }

    /**
     * 根据页面滑动更新选中的标签
     */
    fun updateTabByPage(index: Int) {
        if (_selectedTabIndex.value != index) {
            _selectedTabIndex.value = index
            loadTabDataIfNeeded(index)
        }
    }

    /**
     * 通知标签切换动画已完成
     */
    fun notifyAnimationCompleted() {
        _isAnimatingTabChange.value = false
    }

    // --- 用户交互事件 ---
    fun onRefresh(index: Int) {
        val state = _tabUiStates[index].value
        if (state.loadMoreState == LoadMoreState.Loading) return

        updateTabState(index) { it.copy(isRefreshing = true, page = 1) }
        loadListData(index)
    }

    fun onLoadMore(index: Int) {
        val state = _tabUiStates[index].value
        if (state.loadMoreState != LoadMoreState.PullToLoad) return

        updateTabState(index) { it.copy(loadMoreState = LoadMoreState.Loading, page = it.page + 1) }
        loadListData(index)
    }

    fun shouldTriggerLoadMore(lastIndex: Int, totalCount: Int, tabIndex: Int): Boolean {
        val state = _tabUiStates[tabIndex].value
        return lastIndex >= totalCount - 3 &&
                state.loadMoreState != LoadMoreState.Loading &&
                state.loadMoreState != LoadMoreState.NoMore &&
                state.newsList.isNotEmpty()
    }

    fun retryRequest(index: Int) {
        updateTabState(index) { it.copy(page = 1, loadMoreState = LoadMoreState.Loading) }
        loadListData(index)
    }

    /**
     * 跳转到新闻详情页面
     */
    fun toNewsPage(url: String) {
       navigate(CommonRoutes.Web(url = url))
    }
}
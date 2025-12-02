package com.dalingge.coinvista.main.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.viewModelScope
import com.dalingge.coinvista.core.common.base.state.LoadMoreState
import com.dalingge.coinvista.core.common.base.viewmodel.BaseViewModel
import com.dalingge.coinvista.core.common.result.ResultHandler
import com.dalingge.coinvista.core.common.result.asResult
import com.dalingge.coinvista.core.common.result.Result
import com.dalingge.coinvista.core.data.repository.InsightsRepository
import com.dalingge.coinvista.core.data.repository.MarketRepository
import com.dalingge.coinvista.core.data.repository.NFTsRepository
import com.dalingge.coinvista.core.data.state.AppState
import com.dalingge.coinvista.core.model.entity.FearGreed
import com.dalingge.coinvista.core.model.entity.MarketsCap
import com.dalingge.coinvista.core.model.entity.MarketsCoins
import com.dalingge.coinvista.core.model.entity.NFTsTrending
import com.dalingge.coinvista.core.model.response.NetworkPageData
import com.dalingge.coinvista.core.model.response.NetworkPageMeta
import com.dalingge.coinvista.main.model.MarketTab
import com.dalingge.coinvista.main.model.MarketTabUiState
import com.dalingge.coinvista.main.model.RankType
import com.dalingge.coinvista.main.model.TabViewState
import com.dalingge.coinvista.navigation.AppNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.emptyList

class HomeViewModel(
    navigator: AppNavigator,
    appState: AppState,
    private val marketRepository: MarketRepository,
    private val insightsRepository: InsightsRepository,
    private val nftsRepository: NFTsRepository,
) : BaseViewModel(navigator, appState), DefaultLifecycleObserver {

    // 定义 Tab 结构
    val tabs = listOf(
        MarketTab.CryptoRank(0, RankType.TOP),
        MarketTab.CryptoRank(1, RankType.HOT),
        MarketTab.Watchlist(2),
        MarketTab.CryptoRank(3, RankType.GAINERS),
        MarketTab.CryptoRank(4, RankType.LOSERS),
        MarketTab.Categories(5),
        MarketTab.Exchanges(6),
        MarketTab.Nft(7),
    )

    private val _marketsCapState: MutableStateFlow<MarketsCap> = MutableStateFlow(MarketsCap())
    val marketsCapStat: StateFlow<MarketsCap> = _marketsCapState.asStateFlow()

    private val _fearGreedState: MutableStateFlow<FearGreed> = MutableStateFlow(FearGreed())
    val fearGreedState: StateFlow<FearGreed> = _fearGreedState.asStateFlow()

    // 当前选中的标签索引
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    /**
     * 是否正在进行标签切换动画
     */
    private val _isAnimatingTabChange = MutableStateFlow(false)
    val isAnimatingTabChange: StateFlow<Boolean> = _isAnimatingTabChange.asStateFlow()

    // key: Tab 的 index, value: 该 Tab 的状态
    private val _tabStates = tabs.associate { it.index to MutableStateFlow(TabViewState()) }

    // 对外暴露获取特定 Tab 状态的方法
    fun getTabState(index: Int): StateFlow<TabViewState> {
        return _tabStates[index]?.asStateFlow()
            ?: MutableStateFlow(TabViewState()) // Fallback
    }

    /**
     * 每个标签页的页码
     */
//    private val pageIndices = MutableList(tabs.size) { 1 }
//
//    /**
//     * 标记每个标签页是否已加载过数据
//     */
//    private val tabDataLoaded = MutableList(tabs.size) { false }
//
//    /**
//     * 每个标签页的网络请求UI状态
//     */
//    private val _uiStates = tabs.map {
//        MutableStateFlow<BaseNetWorkListUiState>(BaseNetWorkListUiState.Loading)
//    }
//    val uiStates: List<StateFlow<BaseNetWorkListUiState>> = _uiStates.map { it.asStateFlow() }
//
//    /**
//     * 每个标签页的列表数据
//     */
//    private val _listDataMap = tabs.map {
//        MutableStateFlow<List<MarketsCoins>>(emptyList())
//    }
//    val listDataMap: List<StateFlow<List<MarketsCoins>>> = _listDataMap.map { it.asStateFlow() }
//
//    /**
//     * 每个标签页的下拉刷新状态
//     */
//    private val _refreshingStates = tabs.map {
//        MutableStateFlow(false)
//    }
//    val refreshingStates: List<StateFlow<Boolean>> = _refreshingStates.map { it.asStateFlow() }
//
//    /**
//     * 每个标签页的加载更多状态
//     */
//    private val _loadMoreStates = tabs.map {
//        MutableStateFlow<LoadMoreState>(LoadMoreState.PullToLoad)
//    }
//    val loadMoreStates: List<StateFlow<LoadMoreState>> = _loadMoreStates.map { it.asStateFlow() }

    init {
        requestGreedState()
        requestFearGreed()


        // 加载当前选中标签页的数据
        loadTabDataIfNeeded(_selectedTabIndex.value)
    }

    fun requestGreedState() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = marketRepository.getMarketsCap().asResult(),
            onData = {
                _marketsCapState.value = it
            }
        )

    }

    fun requestFearGreed() {
        ResultHandler.handleResultWithData(
            scope = viewModelScope,
            flow = insightsRepository.getFearAndGreed().asResult(),
            onData = {
                _fearGreedState.value = it
            }
        )
    }

    /**
     * 加载数据（如果未加载）
     */
    private fun loadTabDataIfNeeded(index: Int) {
        val currentState = _tabStates[index]?.value ?: return
        if (!currentState.hasLoaded) {
            loadListData(index)
        }
    }

    /**
     * 加载指定标签页的列表数据
     */
    private fun loadListData(index: Int) {
        // 设置UI状态 - 仅首次加载显示加载中状态
        val tab = tabs.getOrNull(index) ?: return
        val currentState = _tabStates[index]?.value ?: return

        // 更新 UI 为 Loading (仅首次)
        if (currentState.page == 1 && currentState.loadMoreState != LoadMoreState.Loading) {
            updateTabState(index) { it.copy(uiState = MarketTabUiState.Loading) }
        }

        // 根据不同的 Tab 类型调用不同的接口
        when (tab) {
            is MarketTab.CryptoRank-> {
                fetchCryptoData(index, tab, currentState.page)
            }

            is MarketTab.Exchanges -> {
                // fetchExchangeData(index)
            }

            // 3. 返回 NftItem 类型的数据
            is MarketTab.Nft -> {
                fetchNftData(index, currentState.page)
            }
            is MarketTab.Watchlist -> {
                // 自选通常是监听数据库，不需要分页逻辑，或者逻辑不同
                observeWatchlist()
            }

            else -> {

            }
        }
    }

    // --- 具体的数据获取方法 ---

    // 1. 获取通用币种列表 (Rank, Hot, Categories)
    private fun fetchCryptoData(index: Int, tab: MarketTab, page: Int) {
        val flow = marketRepository.getCoins(page, index).asResult()

        handleResult(index, flow) { response ->
            val newItems = response?.result ?: emptyList()
            val meta = response?.meta ?: NetworkPageMeta()

            processSuccess(index, newItems, meta) { oldData, newData ->
                // 数据合并逻辑
                val oldList = (oldData as? MarketTabUiState.CryptoList)?.data ?: emptyList()
                MarketTabUiState.CryptoList(oldList + newData)
            }
        }
    }

    //获取交易所 (NFTsTrending)
    private fun fetchExchangeData(index: Int) {
        // flow 类型: Flow<Result<NetworkPageData<ExchangeItem>>>
        val flow = marketRepository.getTickersExchanges().asResult()

//        handleResult(index, flow) { response ->
//            val items = response?.result ?: emptyList()
//
//            updateTabState(index) { currentState ->
//                currentState.copy(
//                    hasLoaded = true,
//                    isRefreshing = false,
//                    uiState = if (items.isEmpty()) MarketTabUiState.Empty else MarketTabUiState.ExchangeList(items),
//                    loadMoreState = LoadMoreState.NoMore // 强制无加载更多
//                )
//            }
//        }
    }

     //获取 NFT 列表 (NFTsTrending)
    private fun fetchNftData(index: Int, page: Int) {
        val flow = nftsRepository.getNFTsTrending(page).asResult()

        // 编译器现在知道这里的 T 是 NftItem
        handleResult(index, flow) { response ->
            val newItems = response?.result ?: emptyList()
            val meta = response?.meta

            processSuccess(index, newItems, meta) { oldUiState, newData ->
                val oldList = (oldUiState as? MarketTabUiState.NftList)?.data ?: emptyList()
                MarketTabUiState.NftList(oldList + newData)
            }
        }
    }


    // 4. 监听自选 (数据库流)
    private fun observeWatchlist() {

    }

    // --- 通用处理逻辑 ---

    /**
     * 通用的结果处理器
     */
    private fun <T> handleResult(
        index: Int,
        flow: Flow<Result<NetworkPageData<T>>>,
        onSuccess: (NetworkPageData<T>?) -> Unit,
    ) {
        ResultHandler.handleResult(
            scope = viewModelScope,
            flow = flow,
            onSuccessWithData = onSuccess,
            onError = { msg, _ -> handleError(index, msg) }
        )
    }

    /**
     * 通用的成功状态处理器 (处理分页、状态合并)
     * @param dataMerger: 一个 lambda，用于将旧的 UiState 数据和新的 List 数据合并
     */
    private fun <T> processSuccess(
        index: Int,
        newItems: List<T>?,
        meta: NetworkPageMeta?,
        dataMerger: (MarketTabUiState, List<T>) -> MarketTabUiState,
    ) {
        val safeItems = newItems ?: emptyList()
        val hasNext = meta?.hasNextPage == true

        updateTabState(index) { currentState ->
            val newStateData = if (currentState.page == 1) {
                // 第一页：直接覆盖，根据类型包装
                if (safeItems.isEmpty()) MarketTabUiState.Empty
                else dataMerger(MarketTabUiState.Loading, safeItems) // 这里的 Loading 只是为了占位，merger 会处理
            } else {
                // 加载更多：合并数据
                dataMerger(currentState.uiState, safeItems)
            }

            currentState.copy(
                hasLoaded = true,
                isRefreshing = false,
                uiState = newStateData,
                loadMoreState = if (hasNext) LoadMoreState.PullToLoad else LoadMoreState.NoMore
            )
        }
    }

    private fun handleError(index: Int, msg: String?) {
        updateTabState(index) { currentState ->
            val isFirstPage = currentState.page == 1
            currentState.copy(
                isRefreshing = false,
                uiState = if (isFirstPage) MarketTabUiState.Error(msg ?: "Unknown Error") else currentState.uiState,
                loadMoreState = if (isFirstPage) LoadMoreState.PullToLoad else LoadMoreState.Error
            )
        }
        if (_tabStates[index]?.value?.page != 1) {
            // 如果是加载更多失败，页码回退
            updateTabState(index) { it.copy(page = (it.page - 1).coerceAtLeast(1)) }
        }
    }

    // --- 用户交互事件 ---

    fun onRefresh(index: Int) {
        val state = _tabStates[index]?.value ?: return
        if (state.loadMoreState == LoadMoreState.Loading) return

        updateTabState(index) { it.copy(isRefreshing = true, page = 1) }
        loadListData(index)
    }

    fun onLoadMore(index: Int) {
        val state = _tabStates[index]?.value ?: return
        if (state.loadMoreState != LoadMoreState.PullToLoad) return

        updateTabState(index) { it.copy(loadMoreState = LoadMoreState.Loading, page = it.page + 1) }
        loadListData(index)
    }

    fun shouldTriggerLoadMore(lastIndex: Int, totalCount: Int, tabIndex: Int): Boolean {
        val state = _tabStates[tabIndex]?.value ?: return false
        return lastIndex >= totalCount - 3 &&
                state.loadMoreState == LoadMoreState.PullToLoad &&
                !state.isRefreshing
    }

    fun retryRequest(index: Int) {
        updateTabState(index) { it.copy(page = 1, loadMoreState = LoadMoreState.Loading) }
        loadListData(index)
    }

    /**
     * 处理成功响应
     */
//    private fun handleSuccess(tabIndex: Int, data: NetworkPageData<MarketsCoins>?) {
//        val newList = data?.result ?: emptyList()
//        val meta = data?.meta ?: NetworkPageMeta()
//
//        when {
//            pageIndices[tabIndex] == 1 -> {
//                // 刷新或首次加载 - 重置列表
//                _listDataMap[tabIndex].value = newList
//                _refreshingStates[tabIndex].value = false
//
//                // 更新加载状态
//                if (newList.isEmpty()) {
//                    _uiStates[tabIndex].value = BaseNetWorkListUiState.Empty
//                } else {
//                    _uiStates[tabIndex].value = BaseNetWorkListUiState.Success
//                    _loadMoreStates[tabIndex].value = if (meta.hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
//                }
//            }
//
//            else -> {
//                // 加载更多 - 先显示加载成功，延迟更新数据
//                viewModelScope.launch {
//                    _loadMoreStates[tabIndex].value = LoadMoreState.Success
//                    delay(400)
//                    _listDataMap[tabIndex].value += newList
//                    _loadMoreStates[tabIndex].value =
//                        if (meta.hasNextPage) LoadMoreState.PullToLoad else LoadMoreState.NoMore
//                }
//            }
//        }
//    }
//
//    /**
//     * 处理错误响应
//     */
//    private fun handleError(tabIndex: Int, message: String?, exception: Throwable?) {
//        _refreshingStates[tabIndex].value = false
//
//        if (pageIndices[tabIndex] == 1) {
//            // 首次加载或刷新失败
//            if (_listDataMap[tabIndex].value.isEmpty()) {
//                _uiStates[tabIndex].value = BaseNetWorkListUiState.Error
//            }
//            _loadMoreStates[tabIndex].value = LoadMoreState.PullToLoad
//        } else {
//            // 加载更多失败，回退页码
//            pageIndices[tabIndex]--
//            _loadMoreStates[tabIndex].value = LoadMoreState.Error
//        }
//    }
//
//    /**
//     * 重试加载
//     */
//    fun retryRequest(tabIndex: Int = _selectedTabIndex.value) {
//        pageIndices[tabIndex] = 1
//        _loadMoreStates[tabIndex].value = LoadMoreState.Loading
//        loadListData(tabIndex)
//    }
//
//    /**
//     * 触发下拉刷新
//     */
//    fun onRefresh(tabIndex: Int = _selectedTabIndex.value) {
//        // 如果正在加载中，则不重复请求
//        if (_loadMoreStates[tabIndex].value == LoadMoreState.Loading) {
//            return
//        }
//
//        _refreshingStates[tabIndex].value = true
//        pageIndices[tabIndex] = 1
//        loadListData(tabIndex)
//    }
//
//    /**
//     * 加载更多数据
//     */
//    fun onLoadMore(tabIndex: Int = _selectedTabIndex.value) {
//        // 只有在可加载更多和加载失败状态下才能触发加载
//        if (_loadMoreStates[tabIndex].value == LoadMoreState.Loading ||
//            _loadMoreStates[tabIndex].value == LoadMoreState.NoMore ||
//            _loadMoreStates[tabIndex].value == LoadMoreState.Success
//        ) {
//            return
//        }
//
//        _loadMoreStates[tabIndex].value = LoadMoreState.Loading
//        pageIndices[tabIndex]++
//        loadListData(tabIndex)
//    }
//
//    /**
//     * 判断是否应该触发加载更多
//     */
//    fun shouldTriggerLoadMore(
//        lastIndex: Int,
//        totalCount: Int,
//        tabIndex: Int = _selectedTabIndex.value
//    ): Boolean {
//        return lastIndex >= totalCount - 3 &&
//                _loadMoreStates[tabIndex].value != LoadMoreState.Loading &&
//                _loadMoreStates[tabIndex].value != LoadMoreState.NoMore &&
//                _listDataMap[tabIndex].value.isNotEmpty()
//    }


    /**
     * 通知标签切换动画已完成
     */
    fun notifyAnimationCompleted() {
        _isAnimatingTabChange.value = false
    }

    private fun updateTabState(index: Int, update: (TabViewState) -> TabViewState) {
        _tabStates[index]?.update(update)
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
     * 跳转到搜索页面
     */
    fun toSearch() {

    }

    /**
     * 跳转到 GitHub 页面
     */
    fun toGitHubPage() {
        val url = "https://github.com/Joker-x-dev/CoolMallKotlin"
        val title = "GitHub"
//        super.toPage(
//            "${CommonRoutes.WEB}?url=${
//                java.net.URLEncoder.encode(
//                    url,
//                    "UTF-8"
//                )
//            }&title=${java.net.URLEncoder.encode(title, "UTF-8")}"
//        )
    }

}
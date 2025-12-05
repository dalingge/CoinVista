package com.dalingge.coinvista.main.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.dalingge.coinvista.core.common.base.state.LoadMoreState
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.design.theme.BgContentLight
import com.dalingge.coinvista.core.design.theme.BodyMedium
import com.dalingge.coinvista.core.design.theme.DisplayMedium
import com.dalingge.coinvista.core.design.theme.MarkerGreenColor
import com.dalingge.coinvista.core.design.theme.MarkerRedColor
import com.dalingge.coinvista.core.design.theme.ShapeMedium
import com.dalingge.coinvista.core.design.theme.ShapeXSmall
import com.dalingge.coinvista.core.design.theme.SpacePaddingMedium
import com.dalingge.coinvista.core.design.theme.SpacePaddingSmall
import com.dalingge.coinvista.core.design.theme.SpaceVerticalSmall
import com.dalingge.coinvista.core.design.theme.TextPrimaryLight
import com.dalingge.coinvista.core.design.theme.TextSecondaryLight
import com.dalingge.coinvista.core.design.theme.TextTertiaryLight
import com.dalingge.coinvista.core.design.theme.TitleLarge
import com.dalingge.coinvista.core.design.theme.TitleMedium
import com.dalingge.coinvista.core.model.entity.FearGreed
import com.dalingge.coinvista.core.model.entity.MarketsCap
import com.dalingge.coinvista.core.model.entity.MarketsCategories
import com.dalingge.coinvista.core.model.entity.MarketsCoins
import com.dalingge.coinvista.core.model.entity.NFTsTrending
import com.dalingge.coinvista.core.model.entity.TickersExchanges
import com.dalingge.coinvista.core.ui.componet.chart.FearGreedGauge
import com.dalingge.coinvista.core.ui.componet.divider.VDivider
import com.dalingge.coinvista.core.ui.componet.empty.EmptyData
import com.dalingge.coinvista.core.ui.componet.empty.EmptyError
import com.dalingge.coinvista.core.ui.componet.image.NetWorkImage
import com.dalingge.coinvista.core.ui.componet.layout.CoordinatorLayout
import com.dalingge.coinvista.core.ui.componet.layout.CoordinatorState
import com.dalingge.coinvista.core.ui.componet.layout.rememberCoordinatorState
import com.dalingge.coinvista.core.ui.componet.loading.PageLoading
import com.dalingge.coinvista.core.ui.componet.refresh.RefreshLayout
import com.dalingge.coinvista.core.ui.componet.refresh.rememberRefreshState
import com.dalingge.coinvista.core.ui.componet.scaffold.CommonScaffold
import com.dalingge.coinvista.core.ui.componet.tab.ScrollableTextTabComponent
import com.dalingge.coinvista.core.util.extension.toCompactMoney
import com.dalingge.coinvista.feature.main.R
import com.dalingge.coinvista.main.model.MarketTab
import com.dalingge.coinvista.main.model.MarketTabState
import com.dalingge.coinvista.main.model.MarketTabUiState
import com.dalingge.coinvista.main.viewmodel.MarketViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MarketRoute(viewModel: MarketViewModel = koinViewModel()) {

    // 获取生命周期所有者
    val lifecycleOwner = LocalLifecycleOwner.current

    // 注册生命周期观察者
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(viewModel)
        }
    }
    val marketsCapState by viewModel.marketsCapStat.collectAsState()
    val fearGreedState by viewModel.fearGreedState.collectAsState()

    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val isAnimatingTabChange by viewModel.isAnimatingTabChange.collectAsState()

    val coordinatorState = rememberCoordinatorState()

    // 获取标签页状态提供者
    val tabStateProvider: @Composable (Int) -> MarketTabState = { index ->
        val viewState by viewModel.getTabState(index).collectAsState()

        val canRefresh = remember(coordinatorState.collapsedHeight) {
            { coordinatorState.collapsedHeight == 0f }
        }
        MarketTabState(
            uiState = viewState.uiState,
            isRefreshing = viewState.isRefreshing,
            loadMoreState = viewState.loadMoreState,
            onRetry = { viewModel.retryRequest(index) },
            onRefresh = { viewModel.onRefresh(index) },
            onLoadMore = { viewModel.onLoadMore(index) },
            shouldTriggerLoadMore = { lastIndex, totalCount ->
                viewModel.shouldTriggerLoadMore(lastIndex, totalCount, index)
            },
            enablePullToRefresh = canRefresh
        )
    }

    MarketScreen(
        marketsCap = marketsCapState,
        fearGreed = fearGreedState,
        tabs = viewModel.tabs,
        coordinatorState = coordinatorState,
        selectedTabIndex = selectedTabIndex,
        isAnimatingTabChange = isAnimatingTabChange,
        toSearch = viewModel::toSearch,
        toGitHubPage = viewModel::toGitHubPage,
        onTabSelected = viewModel::updateSelectedTab,
        onTabByPageChanged = viewModel::updateTabByPage,
        onAnimationCompleted = viewModel::notifyAnimationCompleted,
        tabStateProvider = tabStateProvider
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MarketScreen(
    marketsCap: MarketsCap = MarketsCap(),
    fearGreed: FearGreed = FearGreed(),
    tabs: List<MarketTab> = emptyList(),
    coordinatorState: CoordinatorState = CoordinatorState(),
    selectedTabIndex: Int = 0,
    isAnimatingTabChange: Boolean = false,
    toSearch: () -> Unit = {},
    toGitHubPage: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    onTabByPageChanged: (Int) -> Unit = {},
    onAnimationCompleted: () -> Unit = {},
    tabStateProvider: @Composable (Int) -> MarketTabState = { _ ->
        MarketTabState(
            uiState = MarketTabUiState.Loading,
            isRefreshing = false,
            loadMoreState = LoadMoreState.PullToLoad,
            onRetry = {},
            onRefresh = {},
            onLoadMore = {},
            shouldTriggerLoadMore = { _, _ -> false }
        )
    },
) {
    // 创建TopAppBar的滚动行为
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val lazyListState = rememberLazyListState()
    val contentScrollableState = remember { lazyListState }

    CommonScaffold(
        topBar = { HomeTopAppBar(scrollBehavior, toSearch, toGitHubPage) },
        scrollBehavior = scrollBehavior
    ) { paddingValues ->

        CoordinatorLayout(
            nestedScrollableState = { contentScrollableState },
            state = coordinatorState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            collapsableContent = {
                GlobalStatsSection(marketsCap, fearGreed)
            },
        ) {

            MarketContentView(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                isAnimatingTabChange = isAnimatingTabChange,
                onTabSelected = onTabSelected,
                onTabByPageChanged = onTabByPageChanged,
                onAnimationCompleted = onAnimationCompleted,
                tabStateProvider = tabStateProvider
            )
        }

    }
}


/**
 * 首页顶部导航栏
 * @param scrollBehavior 滚动行为
 * @param toSearch 跳转到商品搜索页
 * @param toGitHubPage 跳转到GitHub页
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    toSearch: () -> Unit,
    toGitHubPage: () -> Unit,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            // 中间搜索框
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(ShapeMedium)
                    .background(BgContentLight)
                    .clickable { toSearch() }
                    .padding(horizontal = 12.dp),

                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "搜索",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = "币种、NFT、钱包、ENS",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

        },
        actions = {
            IconButton(
                onClick = toGitHubPage,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(28.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github),
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

/**
 * 首页内容视图
 *
 * @param selectedTabIndex 当前选中的标签页索引
 * @param isAnimatingTabChange 是否正在执行标签切换动画
 * @param onTabSelected 标签被点击选择时的回调，参数为选中的标签索引
 * @param onTabByPageChanged 通过页面滑动切换标签时的回调，参数为新的标签索引
 * @param onAnimationCompleted 标签切换动画完成时的回调
 */
@Composable
private fun MarketContentView(
    tabs: List<MarketTab>,
    selectedTabIndex: Int = 0,
    isAnimatingTabChange: Boolean = false,
    onTabSelected: (Int) -> Unit = {},
    onTabByPageChanged: (Int) -> Unit = {},
    onAnimationCompleted: () -> Unit = {},
    tabStateProvider: @Composable (Int) -> MarketTabState,
) {

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(selectedTabIndex) { tabs.size }

    // Pager 与 Tab 联动
    // 处理页面状态变化
    HandlePageStateChanges(
        pageState = pagerState,
        selectedTabIndex = selectedTabIndex,
        isAnimatingTabChange = isAnimatingTabChange,
        onTabByPageChanged = onTabByPageChanged,
        onAnimationCompleted = onAnimationCompleted
    )

    Column {

        ScrollableTextTabComponent(
            tabs = tabs.map { it.title },
            selectedIndex = selectedTabIndex,
            onTabSelected = { index ->
                onTabSelected(index)
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        )

        HorizontalDivider(thickness = 5.dp, color = BgContentLight)


        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            beyondViewportPageCount = 1
        ) { page ->
            // 获取当前标签页的状态
            val tabState = tabStateProvider(page)

            AnimatedContent(
                targetState = tabState.uiState,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith
                            fadeOut(animationSpec = tween(300))
                },
                contentKey = { state -> state::class }
            ) { state ->
                when (state) {
                    is MarketTabUiState.Loading -> PageLoading()
                    is MarketTabUiState.Empty -> EmptyData()
                    is MarketTabUiState.Error -> EmptyError(onRetryClick = tabState.onRetry)
                    is MarketTabUiState.CryptoList -> {
                        Column {
                            CryptoListHeader()
                            CryptoListContent(
                                data = state.data,
                                isRefreshing = tabState.isRefreshing,
                                loadMoreState = tabState.loadMoreState,
                                onRefresh = tabState.onRefresh,
                                onLoadMore = tabState.onLoadMore,
                                shouldTriggerLoadMore = tabState.shouldTriggerLoadMore,
                                enablePullToRefresh = tabState.enablePullToRefresh
                            )
                        }
                    }

                    is MarketTabUiState.ExchangeList -> {
                        Column {
                            ExchangeListHeader()
                            ExchangeListContent(
                                data = state.data,
                                isRefreshing = tabState.isRefreshing,
                                loadMoreState = tabState.loadMoreState,
                                onRefresh = tabState.onRefresh,
                                onLoadMore = tabState.onLoadMore,
                                shouldTriggerLoadMore = tabState.shouldTriggerLoadMore,
                                enablePullToRefresh = tabState.enablePullToRefresh
                            )
                        }
                    }

                    is MarketTabUiState.NFTsList -> {
                        Column {
                            NFTsListHeader()
                            NFTsListContent(
                                data = state.data,
                                isRefreshing = tabState.isRefreshing,
                                loadMoreState = tabState.loadMoreState,
                                onRefresh = tabState.onRefresh,
                                onLoadMore = tabState.onLoadMore,
                                shouldTriggerLoadMore = tabState.shouldTriggerLoadMore,
                                enablePullToRefresh = tabState.enablePullToRefresh
                            )
                        }
                    }

                    is MarketTabUiState.CategoriesList -> {
                        Column {
                            CategoriesListHeader()
                            CategoriesListContent(
                                data = state.data,
                                isRefreshing = tabState.isRefreshing,
                                loadMoreState = tabState.loadMoreState,
                                onRefresh = tabState.onRefresh,
                                onLoadMore = tabState.onLoadMore,
                                shouldTriggerLoadMore = tabState.shouldTriggerLoadMore,
                                enablePullToRefresh = tabState.enablePullToRefresh
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GlobalStatsSection(
    marketsCap: MarketsCap,
    fearGreed: FearGreed,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpacePaddingMedium, vertical = SpaceVerticalSmall)
            .height(70.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Row {
            // 市值
            StatItem(
                title = "市值",
                value = marketsCap.marketCap.toCompactMoney(),
                change = "${marketsCap.marketCapChange}%",
                isNegative = marketsCap.marketCapChange < 0,
                modifier = Modifier.weight(1f)
            )

            VDivider()

            StatItem(
                title = "24H成交量",
                value = marketsCap.volume.toCompactMoney(),
                change = "${marketsCap.volumeChange}%",
                isNegative = marketsCap.volumeChange < 0,
                modifier = Modifier.weight(1f)
            )

            VDivider()

            StatItem(
                title = "BTC领先指数",
                value = "${marketsCap.btcDominance}%",
                change = "${marketsCap.btcDominanceChange}%",
                isNegative = marketsCap.btcDominanceChange < 0,
                modifier = Modifier.weight(1f)
            )

            VDivider()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(SpacePaddingSmall)
            ) {

                Text(
                    text = "恐惧贪婪指数",
                    color = TextSecondaryLight,
                    style = BodyMedium,
                    fontSize = 10.sp
                )

                FearGreedGauge(score = fearGreed.now.value, modifier = Modifier.align(Alignment.CenterHorizontally))

            }
        }
    }
}

@Composable
fun StatItem(title: String, value: String, change: String, isNegative: Boolean, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = SpacePaddingSmall, top = SpacePaddingSmall)) {
        Text(
            text = title,
            color = TextSecondaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = value,
            color = TextPrimaryLight,
            style = DisplayMedium
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = change,
            color = if (isNegative) MarkerRedColor else MarkerGreenColor,
            style = TitleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CryptoListHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.White)
    ) {

        Text(
            text = "#",
            modifier = Modifier
                .width(40.dp)
                .align(Alignment.CenterStart),
            color = TextTertiaryLight,
            style = BodyMedium,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )

        Text(
            text = "市值",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 40.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )

        Text(
            text = "价格",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 116.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )

        Text(
            text = "24小时%",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CryptoListContent(
    data: List<MarketsCoins> = emptyList(),
    onMarketCoinsClick: (String) -> Unit = {},
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    enablePullToRefresh: () -> Boolean,
) {

    val refreshState = rememberRefreshState(shouldEnableRefresh = enablePullToRefresh)

    // 每页是一个 LazyColumn
    RefreshLayout(
        refreshState = refreshState,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore
    ) {
        items(
            items = data,
            key = { it.id }
        ) {
            CryptoListItem(item = it, onMarketCoinsClick = onMarketCoinsClick)
        }
    }
}

@Composable
private fun CryptoListItem(
    item: MarketsCoins,
    onMarketCoinsClick: (String) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.White)
            .clickable { onMarketCoinsClick(item.id) }
            .padding(end = 16.dp)) {

        Text(
            text = item.rank,
            modifier = Modifier
                .width(40.dp)
                .align(Alignment.CenterStart),
            color = TextSecondaryLight,
            style = DisplayMedium,
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier
                .padding(start = 40.dp)
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {

            NetWorkImage(
                model = item.icon,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {

                Text(
                    modifier = Modifier.width(100.dp),
                    text = item.symbol,
                    color = TextPrimaryLight,
                    style = DisplayMedium,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = item.marketCap.toCompactMoney(),
                    color = TextTertiaryLight,
                    style = BodyMedium,
                    fontSize = 12.sp
                )
            }
        }

        Text(
            text = "$" + String.format(Locale.US, "%.2f", item.price),
            modifier = Modifier
                .padding(end = 100.dp)
                .align(Alignment.CenterEnd),
            color = if (item.priceChange1d < 0) MarkerRedColor else MarkerGreenColor,
            style = DisplayMedium,
            fontSize = 14.sp
        )

        Box(
            modifier = Modifier
                .size(65.dp, 30.dp)
                .clip(ShapeXSmall)
                .background(if (item.priceChange1d < 0) MarkerRedColor else MarkerGreenColor)
                .align(Alignment.CenterEnd)
        ) {
            Text(
                text = String.format(Locale.US, "%.2f%s", item.priceChange1d, "%").replace("-", ""),
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 13.sp,
                style = DisplayMedium,
                overflow = TextOverflow.Clip,
                maxLines = 1,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExchangeListHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.White)
    ) {

        Text(
            text = "#",
            modifier = Modifier
                .width(40.dp)
                .align(Alignment.CenterStart),
            color = TextTertiaryLight,
            style = BodyMedium,
            textAlign = TextAlign.Center,
            fontSize = 10.sp
        )

        Text(
            text = "交易所",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 40.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )

        Text(
            text = "24小时%",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 116.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )

        Text(
            text = "24小时交易量",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeListContent(
    data: List<TickersExchanges> = emptyList(),
    onMarketCoinsClick: (String) -> Unit = {},
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    enablePullToRefresh: () -> Boolean,
) {
    val refreshState = rememberRefreshState(shouldEnableRefresh = enablePullToRefresh)

    // 每页是一个 LazyColumn
    RefreshLayout(
        refreshState = refreshState,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore
    ) {
        items(
            items = data,
            key = { it.id }
        ) {
            ExchangeListItem(item = it, onMarketCoinsClick = onMarketCoinsClick)
        }
    }
}

@Composable
fun ExchangeListItem(item: TickersExchanges, onMarketCoinsClick: (String) -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.White)
            .clickable { onMarketCoinsClick(item.id) }
            .padding(end = 16.dp)) {

        Row(modifier = Modifier.align(Alignment.CenterStart), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = item.rank.toString(),
                modifier = Modifier
                    .width(40.dp),
                color = TextSecondaryLight,
                style = DisplayMedium,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )

            NetWorkImage(
                model = item.icon,
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.width(100.dp),
                text = item.name,
                color = TextPrimaryLight,
                style = DisplayMedium,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = String.format(Locale.US, "%.2f%s", item.change24h, "%").replace("-", ""),
            modifier = Modifier
                .padding(end = 100.dp)
                .align(Alignment.CenterEnd),
            color = if (item.change24h < 0) MarkerRedColor else MarkerGreenColor,
            style = DisplayMedium,
            fontSize = 14.sp
        )

        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            text = item.volume24h.toCompactMoney(),
            color = TextPrimaryLight,
            style = TitleLarge,
            fontSize = 14.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NFTsListHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.White)
    ) {

        Text(
            text = "低价",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )


        Text(
            text = "24小时交易量",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NFTsListContent(
    data: List<NFTsTrending> = emptyList(),
    onMarketCoinsClick: (String) -> Unit = {},
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    enablePullToRefresh: () -> Boolean,
) {
    val refreshState = rememberRefreshState(shouldEnableRefresh = enablePullToRefresh)

    // 每页是一个 LazyColumn
    RefreshLayout(
        refreshState = refreshState,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore
    ) {
        items(
            items = data,
            key = { it.name }
        ) {
            NFTsListItem(item = it, onMarketCoinsClick = onMarketCoinsClick)
        }
    }
}

@Composable
fun NFTsListItem(item: NFTsTrending, onMarketCoinsClick: (String) -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.White)
            .clickable { onMarketCoinsClick(item.name) }
            .padding(horizontal = 16.dp)) {


        Row(modifier = Modifier.align(Alignment.CenterStart), verticalAlignment = Alignment.CenterVertically) {
            NetWorkImage(
                model = item.img,
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(5.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {

                Text(
                    text = item.name,
                    color = TextPrimaryLight,
                    style = DisplayMedium,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "${item.floorPriceMc} ETH",
                    color = TextTertiaryLight,
                    style = BodyMedium,
                    fontSize = 12.sp
                )
            }
        }

        Column(modifier = Modifier.align(Alignment.CenterEnd), horizontalAlignment = Alignment.End) {

            Text(
                text = String.format(Locale.US, "%.2f ETH", item.volumeMc24h),
                color = TextPrimaryLight,
                style = DisplayMedium,
                fontSize = 14.sp,
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = String.format(Locale.US, "%.2f%s", item.floorPriceChange24h, "%"),
                color = if (item.floorPriceChange24h < 0) MarkerRedColor else MarkerGreenColor,
                style = BodyMedium,
                fontSize = 12.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriesListHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(Color.White)
    ) {

        Text(
            text = "类别",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )

        Text(
            text = "市值",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 116.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )

        Text(
            text = "24小时%",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            color = TextTertiaryLight,
            style = BodyMedium,
            fontSize = 10.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesListContent(
    data: List<MarketsCategories> = emptyList(),
    onMarketCoinsClick: (String) -> Unit = {},
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
    enablePullToRefresh: () -> Boolean,
) {
    val refreshState = rememberRefreshState(shouldEnableRefresh = enablePullToRefresh)

    // 每页是一个 LazyColumn
    RefreshLayout(
        refreshState = refreshState,
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore
    ) {
        items(
            items = data,
            key = { it.categoryId }
        ) {
            CategoriesListItem(item = it, onMarketCoinsClick = onMarketCoinsClick)
        }
    }
}

@Composable
fun CategoriesListItem(item: MarketsCategories, onMarketCoinsClick: (String) -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.White)
            .clickable { onMarketCoinsClick(item.id) }
            .padding(horizontal = 16.dp)) {

        val paddingStart = 15.dp
        item.coins.forEachIndexed { index, coins ->
            NetWorkImage(
                model = coins.ic,
                modifier = Modifier
                    .padding(start = paddingStart * index)
                    .size(25.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .align(Alignment.CenterStart)
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 63.dp)
                .width(100.dp)
                .align(Alignment.CenterStart),
            text = item.title,
            color = TextPrimaryLight,
            style = DisplayMedium,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = item.totalMC.toCompactMoney(),
            modifier = Modifier
                .padding(end = 100.dp)
                .align(Alignment.CenterEnd),
            color = TextPrimaryLight,
            style = DisplayMedium,
            fontSize = 14.sp
        )

        Box(
            modifier = Modifier
                .size(65.dp, 30.dp)
                .clip(ShapeXSmall)
                .background(if (item.totalP24 < 0) MarkerRedColor else MarkerGreenColor)
                .align(Alignment.CenterEnd)
        ) {
            Text(
                text = String.format(Locale.US, "%.2f%s", item.totalP24, "%").replace("-", ""),
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontSize = 13.sp,
                style = DisplayMedium,
                overflow = TextOverflow.Clip,
                maxLines = 1,
            )
        }
    }
}


/**
 * 处理页面状态变化的副作用
 *
 * @param pageState 分页器状态，控制标签页的滑动
 * @param selectedTabIndex 当前选中的标签索引
 * @param isAnimatingTabChange 是否正在执行标签切换动画
 * @param onTabByPageChanged 通过页面滑动切换标签时的回调，参数为新的标签索引
 * @param onAnimationCompleted 标签切换动画完成时的回调
 */
@Composable
fun HandlePageStateChanges(
    pageState: PagerState,
    selectedTabIndex: Int,
    isAnimatingTabChange: Boolean,
    onTabByPageChanged: (Int) -> Unit,
    onAnimationCompleted: () -> Unit,
) {
    // 当标签选择变化时，自动滚动到相应页面
    LaunchedEffect(selectedTabIndex, isAnimatingTabChange) {
        if (isAnimatingTabChange && pageState.currentPage != selectedTabIndex) {
            pageState.animateScrollToPage(selectedTabIndex)
        }
    }

    // 监听分页器当前页面变化
    LaunchedEffect(pageState.currentPage) {
        // 当页面已经切换到新页面，立即更新导航状态
        if (!isAnimatingTabChange) {
            onTabByPageChanged(pageState.currentPage)
        }
    }

    // 监听滑动动画完成
    LaunchedEffect(pageState.isScrollInProgress) {
        if (!pageState.isScrollInProgress && isAnimatingTabChange) {
            // 当页面滑动动画结束，通知完成
            onAnimationCompleted()
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun MarketScreenPreview() {
    AppTheme {
        MarketScreen()
    }
}


@Preview(showBackground = true)
@Composable
fun CryptoListItemPreview() {
    AppTheme {
        CryptoListItem(
            item = MarketsCoins()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExchangeListItemPreview() {
    ExchangeListItem(
        item = TickersExchanges(
            rank = 1,
            name = "Binance"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun NFTsListItemPreview() {
    NFTsListItem(
        item = NFTsTrending(
            rank = 1,
            name = "Binance"
        )
    )
}


@Preview(showBackground = true)
@Composable
fun CategoriesListItemPreview() {
    CategoriesListItem(
        item = MarketsCategories(title = "Meme")
    )
}
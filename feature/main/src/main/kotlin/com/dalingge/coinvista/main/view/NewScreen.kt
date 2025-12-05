package com.dalingge.coinvista.main.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.dalingge.coinvista.core.common.base.state.LoadMoreState
import com.dalingge.coinvista.core.design.component.FullScreenBox
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.design.theme.BgContentLight
import com.dalingge.coinvista.core.design.theme.BodyMedium
import com.dalingge.coinvista.core.design.theme.CommonIcon
import com.dalingge.coinvista.core.design.theme.MarkerGreenColor
import com.dalingge.coinvista.core.design.theme.MarkerRedColor
import com.dalingge.coinvista.core.design.theme.SpaceHorizontalSmall
import com.dalingge.coinvista.core.design.theme.SpaceVerticalSmall
import com.dalingge.coinvista.core.design.theme.TextPrimaryLight
import com.dalingge.coinvista.core.design.theme.TextSecondaryLight
import com.dalingge.coinvista.core.design.theme.TextTertiaryLight
import com.dalingge.coinvista.core.design.theme.TitleLarge
import com.dalingge.coinvista.core.model.entity.NewsCoinsItem
import com.dalingge.coinvista.core.model.entity.NewsItem
import com.dalingge.coinvista.core.ui.componet.appbar.CenterTopAppBar
import com.dalingge.coinvista.core.ui.componet.image.NetWorkImage
import com.dalingge.coinvista.core.ui.componet.network.BaseNetWorkListView
import com.dalingge.coinvista.core.ui.componet.refresh.RefreshLayout
import com.dalingge.coinvista.core.ui.componet.scaffold.CommonScaffold
import com.dalingge.coinvista.core.ui.componet.tab.ScrollableTextTabComponent
import com.dalingge.coinvista.core.util.extension.toFriendlyTime
import com.dalingge.coinvista.feature.main.R
import com.dalingge.coinvista.main.model.NewsState
import com.dalingge.coinvista.main.model.NewsTabState
import com.dalingge.coinvista.main.viewmodel.NewViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun NewRoute(viewModel: NewViewModel = koinViewModel()) {

    // 获取生命周期所有者
    val lifecycleOwner = LocalLifecycleOwner.current

    // 注册生命周期观察者
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(viewModel)
        }
    }

    val selectedTabIndex by viewModel.selectedTabIndex.collectAsState()
    val isAnimatingTabChange by viewModel.isAnimatingTabChange.collectAsState()

    // 获取标签页状态提供者
    val tabStateProvider: @Composable (Int) -> NewsTabState = { index ->
        val tabUiStates by viewModel.tabUiStates[index].collectAsState()

        NewsTabState(
            uiState = tabUiStates.uiState,
            newsList = tabUiStates.newsList,
            isRefreshing = tabUiStates.isRefreshing,
            loadMoreState = tabUiStates.loadMoreState,
            onRetry = { viewModel.retryRequest(index) },
            onRefresh = { viewModel.onRefresh(index) },
            onLoadMore = { viewModel.onLoadMore(index) },
            shouldTriggerLoadMore = { lastIndex, totalCount ->
                viewModel.shouldTriggerLoadMore(lastIndex, totalCount, index)
            }
        )
    }

    NewScreen(
        selectedTabIndex = selectedTabIndex,
        isAnimatingTabChange = isAnimatingTabChange,
        onTabSelected = viewModel::updateSelectedTab,
        onTabByPageChanged = viewModel::updateTabByPage,
        onAnimationCompleted = viewModel::notifyAnimationCompleted,
        tabStateProvider = tabStateProvider,
        onItemClick = viewModel::toNewsPage
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun NewScreen(
    selectedTabIndex: Int = 0,
    isAnimatingTabChange: Boolean = false,
    onTabSelected: (Int) -> Unit = {},
    onTabByPageChanged: (Int) -> Unit = {},
    onAnimationCompleted: () -> Unit = {},
    tabStateProvider: @Composable (Int) -> NewsTabState = { _ -> NewsTabState() },
    onItemClick: (String) -> Unit = {},
) {


    CommonScaffold(
        topBar = { CenterTopAppBar(titleText = "资讯", showBackIcon = false) },
    ) { paddingValues ->

        FullScreenBox(padding = paddingValues) {
            NewsContentView(
                selectedTabIndex = selectedTabIndex,
                isAnimatingTabChange = isAnimatingTabChange,
                onTabSelected = onTabSelected,
                onTabByPageChanged = onTabByPageChanged,
                onAnimationCompleted = onAnimationCompleted,
                tabStateProvider = tabStateProvider,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun NewsContentView(
    selectedTabIndex: Int = 0,
    isAnimatingTabChange: Boolean = false,
    onTabSelected: (Int) -> Unit = {},
    onTabByPageChanged: (Int) -> Unit = {},
    onAnimationCompleted: () -> Unit = {},
    tabStateProvider: @Composable (Int) -> NewsTabState,
    onItemClick: (String) -> Unit = {},
) {

    // 协程作用域
    val coroutineScope = rememberCoroutineScope()
    // 创建分页器状态
    val pagerState = rememberPagerState(selectedTabIndex) { NewsState.entries.size }

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
            tabs = NewsState.entries.map { it.label },
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
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp),
            beyondViewportPageCount = 1
        ) { page ->

            val tabState = tabStateProvider(page)

            // 使用 BaseNetWorkListView 包裹每个标签页
            BaseNetWorkListView(
                uiState = tabState.uiState,
                onRetry = tabState.onRetry
            ) {
                NewsTabContent(
                    newsList = tabState.newsList,
                    onItemClick = onItemClick,
                    isRefreshing = tabState.isRefreshing,
                    loadMoreState = tabState.loadMoreState,
                    onRefresh = tabState.onRefresh,
                    onLoadMore = tabState.onLoadMore,
                    shouldTriggerLoadMore = tabState.shouldTriggerLoadMore
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsTabContent(
    newsList: List<NewsItem>,
    onItemClick: (String) -> Unit,
    isRefreshing: Boolean,
    loadMoreState: LoadMoreState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    shouldTriggerLoadMore: (lastIndex: Int, totalCount: Int) -> Boolean,
) {

    RefreshLayout(
        isRefreshing = isRefreshing,
        loadMoreState = loadMoreState,
        onRefresh = onRefresh,
        onLoadMore = onLoadMore,
        shouldTriggerLoadMore = shouldTriggerLoadMore
    ) {
        items(
            items = newsList,
            key = { it.id }
        ) {
            NewsListItem(it, onItemClick)
        }
    }
}


@Composable
fun NewsListItem(item: NewsItem, onItemClick: (String) -> Unit = {}) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .clickable { onItemClick(item.link) }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {

        NetWorkImage(
            model = item.imgUrl,
            size = 80.dp,
            cornerShape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = item.title,
                color = TextPrimaryLight,
                style = TitleLarge,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            Row {
                item.coins.take(2).forEach {
                    CoinTag(it)
                    SpaceHorizontalSmall()
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = item.source + " • " + item.feedDate.toFriendlyTime(),
                color = TextTertiaryLight,
                style = BodyMedium,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun CoinTag(item: NewsCoinsItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(18.dp)
            .background(Color(0xFFEEF3F6), CircleShape)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = item.coinKeyWords,
            color = TextSecondaryLight,
            lineHeight = 18.sp,
            fontSize = 12.sp
        )

        CommonIcon(
            R.drawable.ic_arrow_down,
            modifier = Modifier
                .size(12.dp)
                .rotate(if (item.coinPercent < 0) 0f else 180f),
            tint = if (item.coinPercent < 0) MarkerRedColor else MarkerGreenColor
        )

        Text(
            text = String.format(Locale.US, "%.2f%s", item.coinPercent, "%").replace("-", ""),
            color = if (item.coinPercent < 0) MarkerRedColor else MarkerGreenColor,
            lineHeight = 18.sp,
            fontSize = 12.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NewScreenPreview() {
    AppTheme {
        NewScreen()
    }
}


@Preview(showBackground = true)
@Composable
fun NewsListItemPreview() {
    AppTheme {
        NewsListItem(
            item = NewsItem(
                title = "Dogecoin Price Alert: Why $0.20 Is Battle Line after 71K Address Surge",
                source = "Coinspeaker",
                feedDate = 1764923414000,
                coins = arrayListOf(
                    NewsCoinsItem(
                        coinKeyWords = "ETH"
                    )
                )
            )
        )
    }
}
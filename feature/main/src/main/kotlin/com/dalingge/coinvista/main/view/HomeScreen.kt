package com.dalingge.coinvista.main.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.design.theme.BgContentLight
import com.dalingge.coinvista.core.design.theme.BodyMedium
import com.dalingge.coinvista.core.design.theme.DisplayMedium
import com.dalingge.coinvista.core.design.theme.MarkerGreenColor
import com.dalingge.coinvista.core.design.theme.MarkerRedColor
import com.dalingge.coinvista.core.design.theme.RightArrowGray
import com.dalingge.coinvista.core.design.theme.ShapeMedium
import com.dalingge.coinvista.core.design.theme.SpaceDivider
import com.dalingge.coinvista.core.design.theme.SpacePaddingMedium
import com.dalingge.coinvista.core.design.theme.SpacePaddingSmall
import com.dalingge.coinvista.core.design.theme.SpacePaddingXSmall
import com.dalingge.coinvista.core.design.theme.SpaceVerticalLarge
import com.dalingge.coinvista.core.design.theme.SpaceVerticalSmall
import com.dalingge.coinvista.core.design.theme.SpaceVerticalXSmall
import com.dalingge.coinvista.core.design.theme.TextPrimaryLight
import com.dalingge.coinvista.core.design.theme.TextSecondaryLight
import com.dalingge.coinvista.core.design.theme.TitleMedium
import com.dalingge.coinvista.core.ui.componet.divider.VDivider
import com.dalingge.coinvista.core.ui.componet.scaffold.CommonScaffold
import com.dalingge.coinvista.core.ui.componet.tab.ScrollableTextTabComponent
import com.dalingge.coinvista.core.ui.componet.text.AppText
import com.dalingge.coinvista.core.ui.componet.text.StockPriceText
import com.dalingge.coinvista.feature.main.R
import com.dalingge.coinvista.main.model.HomeTab
import com.dalingge.coinvista.main.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeRoute(viewModel: HomeViewModel = koinViewModel()) {

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


    HomeScreen(
        selectedTabIndex = selectedTabIndex,
        isAnimatingTabChange = isAnimatingTabChange,
        toSearch = viewModel::toSearch,
        toGitHubPage = viewModel::toGitHubPage,
        onTabSelected = viewModel::updateSelectedTab,
        onTabByPageChanged = viewModel::updateTabByPage,
        onAnimationCompleted = viewModel::notifyAnimationCompleted,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    selectedTabIndex: Int = 0,
    isAnimatingTabChange: Boolean = false,
    toSearch: () -> Unit = {},
    toGitHubPage: () -> Unit = {},
    onTabSelected: (Int) -> Unit = {},
    onTabByPageChanged: (Int) -> Unit = {},
    onAnimationCompleted: () -> Unit = {},
) {
    // 创建TopAppBar的滚动行为
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(selectedTabIndex) { HomeTab.entries.size }

    CommonScaffold(
        topBar = {
            HomeTopAppBar(scrollBehavior, toSearch, toGitHubPage)
        },
        scrollBehavior = scrollBehavior
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = SpacePaddingMedium),
                border = BorderStroke(SpaceDivider, MaterialTheme.colorScheme.outline)
                //elevation = CardDefaults.cardElevation(defaultElevation = SpacePaddingXSmall)
            ) {

                Row {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(SpacePaddingSmall)
                    ) {

                        Text(
                            text = "市值",
                            color = TextSecondaryLight,
                            style = BodyMedium,
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "$3.40T",
                            color = TextPrimaryLight,
                            style = DisplayMedium
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "1.45%",
                            color = MarkerGreenColor,
                            style = TitleMedium
                        )
                    }

                    VDivider()

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(SpacePaddingSmall)
                    ) {

                        Text(
                            text = "24H成交量",
                            color = TextSecondaryLight,
                            style = BodyMedium,
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "$282.4B",
                            color = TextPrimaryLight,
                            style = DisplayMedium
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "1.45%",
                            color = MarkerRedColor,
                            style = TitleMedium
                        )

                    }
                    VDivider()

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(SpacePaddingSmall)
                    ) {

                        Text(
                            text = "BTC领先指数",
                            color = TextSecondaryLight,
                            style = BodyMedium,
                            fontSize = 10.sp
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "$282.4B",
                            color = TextPrimaryLight,
                            style = DisplayMedium
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "1.45%",
                            color = MarkerRedColor,
                            style = TitleMedium
                        )

                    }

                    VDivider()

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(SpacePaddingSmall)
                    ) {
                        Text(
                            text = "恐惧贪婪指数",
                            color = TextSecondaryLight,
                            style = BodyMedium,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            SpaceVerticalSmall()

            // Pager 与 Tab 联动
            // 处理页面状态变化
            HandlePageStateChanges(
                pageState = pagerState,
                selectedTabIndex = selectedTabIndex,
                isAnimatingTabChange = isAnimatingTabChange,
                onTabByPageChanged = onTabByPageChanged,
                onAnimationCompleted = onAnimationCompleted
            )

            ScrollableTextTabComponent(
                tabs = HomeTab.entries.map { it.label },
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
            ) { page ->
            }

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
 * 处理页面状态变化的副作用
 *
 * @param pageState 分页器状态，控制标签页的滑动
 * @param selectedTabIndex 当前选中的标签索引
 * @param isAnimatingTabChange 是否正在执行标签切换动画
 * @param onTabByPageChanged 通过页面滑动切换标签时的回调，参数为新的标签索引
 * @param onAnimationCompleted 标签切换动画完成时的回调
 */
@Composable
private fun HandlePageStateChanges(
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
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}
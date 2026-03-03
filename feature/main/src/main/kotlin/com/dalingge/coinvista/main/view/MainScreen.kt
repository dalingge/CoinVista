package com.dalingge.coinvista.main.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.design.theme.robotoSansFamily
import com.dalingge.coinvista.main.model.TopLevelDestination
import com.dalingge.coinvista.main.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.forEachIndexed

/**
 *
 * @Description : 主界面路由入口
 * @Author :Dalingge
 * @Time :2025/10/14  15:12
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainRoute(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    viewModel: MainViewModel,
) {
    // 从ViewModel获取当前导航状态
    val currentPageIndex by viewModel.currentPageIndex.collectAsState()

    MainScreen(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        currentPageIndex = currentPageIndex,
        onPageChanged = viewModel::updatePageIndex,
        onNavigationItemSelected = viewModel::updateDestination,
    )

}

/**
 * 主界面
 * 包含底部导航栏和四个主要页面
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MainScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    currentPageIndex: Int = 0,
    onPageChanged: (Int) -> Unit = {},
    onNavigationItemSelected: (Int) -> Unit = {},
) {
    // 协程作用域
    val scope = rememberCoroutineScope()

    // 创建分页器状态
    val pageState = rememberPagerState(initialPage = currentPageIndex) {
        TopLevelDestination.entries.size
    }

    // 监听分页器当前页面变化
    LaunchedEffect(pageState.currentPage) {
        onPageChanged(pageState.currentPage)
    }

    Scaffold(
        // 排除顶部导航栏边距
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(WindowInsets.statusBars),
        bottomBar = {
            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets
            ) {
                TopLevelDestination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = currentPageIndex == index,
                        onClick = {
                            onNavigationItemSelected(index)
                            scope.launch {
                                pageState.scrollToPage(index)
                            }
                        },
                        icon = {
                            Icon(
                                painterResource(id = destination.iconResId),
                                null,
                            )
                        },
                        label = {
                            Text(
                                stringResource(id = destination.titleTextId),
                                fontSize = 12.sp,
                                fontFamily = robotoSansFamily,
                                fontWeight = FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            unselectedIconColor = Color(0xffD7D8E1),
                            selectedIconColor = Color(0xff2B71FF),
                            selectedTextColor = Color(0xff686868), // 选中文本颜色
                            unselectedTextColor = Color(0xff9A999D) // 未选中文本颜色
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        MainScreenContentView(
            pageState = pageState,
            paddingValues = paddingValues,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = animatedContentScope
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun MainScreenContentView(
    pageState: PagerState,
    paddingValues: PaddingValues,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {
    HorizontalPager(
        state = pageState,
        modifier = Modifier.padding(paddingValues),
        userScrollEnabled = false
    ) { page: Int ->
        when (page) {
            0 -> MarketRoute(
                sharedTransitionScope = sharedTransitionScope,
                animatedContentScope = animatedContentScope
            )
            1 -> NewRoute()
            2 -> PortfolioRoute()
            3 -> MineRoute()
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        MainScreen()
    }
}

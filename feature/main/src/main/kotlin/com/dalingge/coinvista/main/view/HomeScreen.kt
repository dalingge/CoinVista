package com.dalingge.coinvista.main.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.design.theme.ShapeMedium
import com.dalingge.coinvista.core.ui.componet.appbar.CenterTopAppBar
import com.dalingge.coinvista.core.ui.componet.scaffold.CommonScaffold
import com.dalingge.coinvista.feature.main.R
import com.dalingge.coinvista.main.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun HomeRoute(viewModel: HomeViewModel = koinViewModel()){

    // 获取生命周期所有者
    val lifecycleOwner = LocalLifecycleOwner.current

    // 注册生命周期观察者
    DisposableEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(viewModel)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(viewModel)
        }
    }
    HomeScreen(
        toSearch = viewModel::toSearch,
        toGitHubPage = viewModel::toGitHubPage,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    toSearch: () -> Unit = {},
    toGitHubPage: () -> Unit = {},
) {
    // 创建TopAppBar的滚动行为
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    CommonScaffold(
        topBar = {
            HomeTopAppBar(scrollBehavior,toSearch,toGitHubPage)
        },
        scrollBehavior = scrollBehavior
    ) { paddingValues ->

    }
}


/**
 * 首页顶部导航栏
 * @param scrollBehavior 滚动行为
 * @param toGoodsSearch 跳转到商品搜索页
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
            Card(
                shape = ShapeMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(ShapeMedium)
                    .clickable { toSearch() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
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
            }
        },
        actions = {
            IconButton(
                onClick = toGitHubPage,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(27.dp)
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


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
      HomeScreen()
    }
}
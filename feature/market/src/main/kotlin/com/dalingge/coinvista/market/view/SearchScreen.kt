package com.dalingge.coinvista.market.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.dalingge.coinvista.core.common.base.state.BaseNetWorkUiState
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.model.entity.SearchList
import com.dalingge.coinvista.core.navigation.NavigationService.navigateBack
import com.dalingge.coinvista.core.ui.componet.appbar.SearchTopAppBar
import com.dalingge.coinvista.core.ui.componet.network.BaseNetWorkView
import com.dalingge.coinvista.core.ui.componet.scaffold.AppScaffold
import com.dalingge.coinvista.market.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel

/**
 *
 * @Description :搜索路由
 * @Author : Dalingge
 * @Time :2025/12/8  11:00
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SearchRoute(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    viewModel: SearchViewModel = koinViewModel(),
) {

    // UI状态
    val uiState by viewModel.uiState.collectAsState()

    SearchScreen(
        onSearch = viewModel::onSearch,
        onRetry = viewModel::retryRequest,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun SearchScreen(
    uiState: BaseNetWorkUiState<List<SearchList>> = BaseNetWorkUiState.Loading,
    onRetry: () -> Unit = {},
    onSearch: (String) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
) {

    AppScaffold(
        topBar = {
            SearchTopAppBar(
                onBackClick = { navigateBack() },
                onSearch = onSearch,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedContentScope
            )
        }
    ) {
        BaseNetWorkView(
            uiState = uiState,
            onRetry = onRetry
        ) { keywordList ->
           MarketSearchContentView()
        }
    }
}

@Composable
private fun MarketSearchContentView() {

}


@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
internal fun GoodsSearchScreenPreview() {
    AppTheme {
        SearchScreen()
    }
}
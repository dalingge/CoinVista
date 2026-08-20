package com.dalingge.coinvista.main.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.ui.componet.appbar.CenterTopAppBar
import com.dalingge.coinvista.core.ui.componet.scaffold.CommonScaffold
import com.dalingge.coinvista.main.viewmodel.PortfolioViewModel
import org.koin.androidx.compose.koinViewModel

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/11/24  18:26
 */

@Composable
internal fun PortfolioRoute(viewModel: PortfolioViewModel = koinViewModel()){
    PortfolioScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PortfolioScreen() {

    CommonScaffold(
        topBar = { CenterTopAppBar(titleText = "投资组合", showBackIcon = false) },
    ) { paddingValues ->
    }
}

@Preview(showBackground = true)
@Composable
fun PortfolioScreenPreview() {
    AppTheme {
        PortfolioScreen()
    }
}
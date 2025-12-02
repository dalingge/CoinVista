package com.dalingge.coinvista.main.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.main.viewmodel.PortfolioViewModel
import org.koin.androidx.compose.koinViewModel

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/11/24  18:26
 */

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun PortfolioRoute(viewModel: PortfolioViewModel = koinViewModel()){

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun PortfolioScreen() {
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun PortfolioScreenPreview() {
    AppTheme {
        PortfolioScreen()
    }
}
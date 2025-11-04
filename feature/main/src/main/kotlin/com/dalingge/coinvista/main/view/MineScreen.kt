package com.dalingge.coinvista.main.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.main.viewmodel.MineViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun MineRoute(viewModel: MineViewModel = koinViewModel()){

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun MineScreen() {
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun MineScreenPreview() {
    AppTheme {
        MineScreen()
    }
}
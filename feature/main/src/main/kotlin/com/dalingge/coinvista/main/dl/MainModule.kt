package com.dalingge.coinvista.main.dl

import com.dalingge.coinvista.core.navigation.routes.MainRoutes
import com.dalingge.coinvista.main.view.MainRoute
import com.dalingge.coinvista.main.view.SplashRoute
import com.dalingge.coinvista.main.viewmodel.MarketViewModel
import com.dalingge.coinvista.main.viewmodel.MainViewModel
import com.dalingge.coinvista.main.viewmodel.NewViewModel
import com.dalingge.coinvista.main.viewmodel.MineViewModel
import com.dalingge.coinvista.main.viewmodel.PortfolioViewModel
import com.dalingge.coinvista.main.viewmodel.SplashViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.viewModel

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/10/14  14:59
 */
@OptIn(KoinExperimentalAPI::class)
val mainModule = module {

    viewModel<SplashViewModel>()
    viewModel<MainViewModel>()
    viewModel<MarketViewModel>()
    viewModel<NewViewModel>()
    viewModel<PortfolioViewModel>()
    viewModel<MineViewModel>()

    navigation<MainRoutes.Splash> {
        SplashRoute(viewModel = koinViewModel())
    }

    navigation<MainRoutes.Main> {
        MainRoute(viewModel = koinViewModel())
    }

}
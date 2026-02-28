package com.dalingge.coinvista.main.dl

import com.dalingge.coinvista.main.viewmodel.MarketViewModel
import com.dalingge.coinvista.main.viewmodel.MainViewModel
import com.dalingge.coinvista.main.viewmodel.NewViewModel
import com.dalingge.coinvista.main.viewmodel.MineViewModel
import com.dalingge.coinvista.main.viewmodel.PortfolioViewModel
import com.dalingge.coinvista.main.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/10/14  14:59
 */
val mainModule = module {

    viewModel { SplashViewModel() }
    viewModel { MainViewModel() }
    viewModel { MarketViewModel(get(), get(), get()) }
    viewModel { NewViewModel(get()) }
    viewModel { PortfolioViewModel() }
    viewModel { MineViewModel() }
}
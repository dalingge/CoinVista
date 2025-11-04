package com.dalingge.coinvista.main.dl

import com.dalingge.coinvista.main.viewmodel.MainViewModel
import com.dalingge.coinvista.main.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/14  14:59
 */
val mainModule = module {

    // ViewModel for Detail View
    viewModel { SplashViewModel(get(), get()) }

    viewModel { MainViewModel(get(), get()) }
}
package com.dalingge.coinvista.market.dl

import com.dalingge.coinvista.market.viewmodel.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/8  11:01
 */
val marketModule = module {


    viewModel {
        SearchViewModel(get())
    }
}
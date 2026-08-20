package com.dalingge.coinvista.market.dl


import com.dalingge.coinvista.market.viewmodel.SearchViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/8  11:01
 */
@OptIn(KoinExperimentalAPI::class)
val marketModule = module {

    viewModel<SearchViewModel>()
}
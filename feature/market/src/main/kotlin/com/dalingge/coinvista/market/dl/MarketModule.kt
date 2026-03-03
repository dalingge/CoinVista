package com.dalingge.coinvista.market.dl

import com.dalingge.coinvista.core.navigation.routes.CommonRoutes
import com.dalingge.coinvista.core.navigation.routes.MarketRoutes
import com.dalingge.coinvista.market.view.SearchRoute
import com.dalingge.coinvista.market.viewmodel.SearchViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/8  11:01
 */
@OptIn(KoinExperimentalAPI::class)
val marketModule = module {


    viewModel {
        SearchViewModel(get())
    }

    navigation<MarketRoutes.Search> {
        SearchRoute()
    }
}
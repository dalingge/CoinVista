package com.dalingge.coinvista.common.dl

import com.dalingge.coinvista.common.view.WebRoute
import com.dalingge.coinvista.common.viewmodel.WebViewModel
import com.dalingge.coinvista.core.navigation.routes.CommonRoutes
import com.dalingge.coinvista.core.navigation.routes.MainRoutes
import org.koin.android.ext.koin.androidApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/5  18:28
 */
@OptIn(KoinExperimentalAPI::class)
val commonModule = module {

    viewModel {(navKey: CommonRoutes.Web) ->
        WebViewModel(navKey,androidApplication())
    }

    navigation<CommonRoutes.Web> {key->
        WebRoute(key)
    }
}
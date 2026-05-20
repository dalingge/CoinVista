package com.dalingge.coinvista.common.dl

import com.dalingge.coinvista.common.view.WebRoute
import com.dalingge.coinvista.common.viewmodel.WebViewModel
import com.dalingge.coinvista.core.navigation.routes.CommonRoutes
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.koin.plugin.module.dsl.viewModel

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/5  18:28
 */
@OptIn(KoinExperimentalAPI::class)
val commonModule = module {

    viewModel<WebViewModel>()


    navigation<CommonRoutes.Web> { key ->
        // WebRoute(key)

        WebRoute(viewModel = koinViewModel { parametersOf(key) })
    }
}
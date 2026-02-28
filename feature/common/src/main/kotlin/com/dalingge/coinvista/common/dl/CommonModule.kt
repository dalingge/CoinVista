package com.dalingge.coinvista.common.dl

import com.dalingge.coinvista.common.viewmodel.WebViewModel
import com.dalingge.coinvista.core.navigation.routes.CommonRoutes
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/5  18:28
 */
val commonModule = module {

    viewModel {(navKey: CommonRoutes.Web) ->
        WebViewModel(navKey,androidApplication())
    }

}
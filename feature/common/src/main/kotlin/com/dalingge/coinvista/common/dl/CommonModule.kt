package com.dalingge.coinvista.common.dl

import com.dalingge.coinvista.common.viewmodel.WebViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
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
}

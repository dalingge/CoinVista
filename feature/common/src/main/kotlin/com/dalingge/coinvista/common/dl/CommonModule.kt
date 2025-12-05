package com.dalingge.coinvista.common.dl

import androidx.lifecycle.SavedStateHandle
import com.dalingge.coinvista.common.viewmodel.WebViewModel
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

    viewModel {(handle: SavedStateHandle) ->
        WebViewModel(get(), get(),handle,androidApplication())
    }

}
package com.dalingge.coinvista.dl

import com.dalingge.coinvista.core.navigation.AppNavigator
import org.koin.dsl.module

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2024/10/31  23:35
 */

val appModule = module {

    single { AppNavigator(get()) }
}


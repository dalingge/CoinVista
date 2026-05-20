package com.dalingge.coinvista.dl

import com.dalingge.coinvista.core.navigation.AppNavigator
import com.dalingge.coinvista.core.network.datasource.market.MarketNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.market.MarketNetworkDataSourceImpl
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2024/10/31  23:35
 */

val appModule = module {

    single<AppNavigator>()
}


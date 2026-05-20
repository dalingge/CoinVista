package com.dalingge.coinvista.core.data.di

import com.dalingge.coinvista.core.data.repository.InsightsRepository
import com.dalingge.coinvista.core.data.repository.MarketRepository
import com.dalingge.coinvista.core.data.repository.NFTsRepository
import com.dalingge.coinvista.core.data.repository.NewsRepository
import com.dalingge.coinvista.core.data.repository.TradingRepository
import com.dalingge.coinvista.core.data.state.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module
import org.koin.plugin.module.dsl.factory
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.create

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/10/14  14:43
 */



fun dispatcherDefault(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

val appStateModule = module {

    single { create(::dispatcherDefault) }

    single<AppState>()

    factory<MarketRepository>()
    factory<InsightsRepository>()
    factory<NFTsRepository>()
    factory<NewsRepository>()
    factory<TradingRepository>()

}

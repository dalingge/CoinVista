package com.dalingge.coinvista.core.data.di

import com.dalingge.coinvista.core.data.repository.InsightsRepository
import com.dalingge.coinvista.core.data.repository.MarketRepository
import com.dalingge.coinvista.core.data.repository.NewsRepository
import com.dalingge.coinvista.core.data.repository.TradingRepository
import com.dalingge.coinvista.core.data.state.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/14  14:43
 */

// 1. 定义限定符
val ApplicationScope = named("ApplicationScope")

// 2. 应用状态模块
val appStateModule = module {
    single<CoroutineScope>(ApplicationScope) {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    single { AppState(get(ApplicationScope)) }

    factory { MarketRepository(get()) }
    factory { InsightsRepository(get()) }
    factory { NewsRepository(get()) }
    factory { TradingRepository(get()) }


    //single { WebSocketRepository(get()) }

}
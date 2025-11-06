package com.dalingge.coinvista.core.data.repository

import com.dalingge.coinvista.core.model.entity.MarketsCap
import com.dalingge.coinvista.core.network.datasource.market.MarketNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/21  11:01
 */
class MarketRepository(
    private val marketNetworkDataSource: MarketNetworkDataSource
) {

    /**
     * 市值
     */
    fun getMarketsCap(): Flow<MarketsCap> =
        flow {
            emit(marketNetworkDataSource.getMarketsCap())
        }.flowOn(Dispatchers.IO)

}



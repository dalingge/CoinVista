package com.dalingge.coinvista.core.data.repository

import com.dalingge.coinvista.core.model.entity.FearGreed
import com.dalingge.coinvista.core.network.datasource.insights.InsightsNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/11/21  18:32
 */
class InsightsRepository(
    private val insightsNetworkDataSource: InsightsNetworkDataSource,
) {

    /**
     * 获取加密货币恐惧与贪婪指数
     */
    fun getFearAndGreed(): Flow<FearGreed> =
        flow {
            emit(insightsNetworkDataSource.getFearAndGreed())
        }.flowOn(Dispatchers.IO)
}
package com.dalingge.coinvista.core.network.datasource.insights

import com.dalingge.coinvista.core.model.entity.FearGreed
import com.dalingge.coinvista.core.network.base.BaseNetworkDataSource
import com.dalingge.coinvista.core.network.service.InsightsService

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/11/21  18:29
 */
class InsightsNetworkDataSourceImpl(private val insightsService: InsightsService) : BaseNetworkDataSource(), InsightsNetworkDataSource {


    override suspend fun getFearAndGreed(): FearGreed {
       return insightsService.getFearAndGreed()
    }
}
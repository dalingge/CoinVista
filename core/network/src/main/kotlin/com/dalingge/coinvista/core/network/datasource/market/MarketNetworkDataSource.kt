package com.dalingge.coinvista.core.network.datasource.market

import com.dalingge.coinvista.core.model.entity.MarketsCap


/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/21  17:36
 */
interface MarketNetworkDataSource {

    /**
     * 市值
     */
    suspend fun getMarketsCap(): MarketsCap

}
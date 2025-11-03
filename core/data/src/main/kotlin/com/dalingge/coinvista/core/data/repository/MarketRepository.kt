package com.dalingge.coinvista.core.data.repository

import com.dalingge.coinvista.core.network.datasource.market.MarketNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.ws.WebSocketDataSource

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/21  11:01
 */
class MarketRepository(
    private val marketNetworkDataSource: MarketNetworkDataSource,
    private val webSocketDataSource: WebSocketDataSource
) {


}



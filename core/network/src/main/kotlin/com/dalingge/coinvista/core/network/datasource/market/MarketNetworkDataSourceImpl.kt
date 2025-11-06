package com.dalingge.coinvista.core.network.datasource.market

import com.dalingge.coinvista.core.model.entity.MarketsCap
import com.dalingge.coinvista.core.network.base.BaseNetworkDataSource
import com.dalingge.coinvista.core.network.service.MarketService

/**
 *
 * @Description : 行情相关数据源实现类
 * @Author :丁博洋
 * @Time :2025/10/21  17:54
 * @property marketService 行情服务接口，用于发起实际的网络请求
 */
class MarketNetworkDataSourceImpl(private val marketService: MarketService): BaseNetworkDataSource(), MarketNetworkDataSource {

    /**
     * 市值
     *
     * @return 结果响应数据
     */
    override suspend fun getMarketsCap(): MarketsCap {
        return marketService.getMarketsCap()
    }


}
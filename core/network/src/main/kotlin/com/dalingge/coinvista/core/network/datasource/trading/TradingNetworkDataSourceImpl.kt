package com.dalingge.coinvista.core.network.datasource.trading

import com.dalingge.coinvista.core.network.base.BaseNetworkDataSource
import com.dalingge.coinvista.core.network.service.TradingService

/**
 *
 * @Description : 交易相关数据源实现类
 * @Author :丁博洋
 * @Time :2025/10/21  17:54
 * @property tradingService 交易服务接口，用于发起实际的网络请求
 */
class TradingNetworkDataSourceImpl(private val tradingService: TradingService): BaseNetworkDataSource(), TradingNetworkDataSource {



}
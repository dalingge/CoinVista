package com.dalingge.coinvista.core.network.datasource.market

import com.dalingge.coinvista.core.model.entity.MarketsCap
import com.dalingge.coinvista.core.model.entity.MarketsCategories
import com.dalingge.coinvista.core.model.entity.MarketsCoins
import com.dalingge.coinvista.core.model.entity.TickersExchanges
import com.dalingge.coinvista.core.model.response.NetworkPageData
import com.dalingge.coinvista.core.network.base.BaseNetworkDataSource
import com.dalingge.coinvista.core.network.service.MarketService

/**
 *
 * @Description : 行情相关数据源实现类
 * @Author :Dalingge
 * @Time :2025/10/21  17:54
 * @property marketService 行情服务接口，用于发起实际的网络请求
 */
class MarketNetworkDataSourceImpl(private val marketService: MarketService) : BaseNetworkDataSource(), MarketNetworkDataSource {

    /**
     * 市值
     *
     * @return 结果响应数据
     */
    override suspend fun getMarketsCap(): MarketsCap {
        return marketService.getMarketsCap()
    }

    /**
     * 加密货币所有数据
     * @param page 要检索的页码（从 1 开始索引）
     * @param limit 每页返回的数量
     * @param sortBy 用于对结果进行排序的字段
     * @param sortDir 对结果进行排序。使用“asc”表示升序排列，使用“desc”表示降序排列。
     * @param rankLessThan 筛选市值排名低于指定数字的加密货币。例如：输入 50，则仅显示市值排名前 50 的加密货币
     * @param priceChange1dGreaterThan 筛选出24小时价格涨幅大于指定百分比的加密货币。例如：筛选出日涨幅超过10%的加密货币
     * @param priceChange1dLessThan 筛选出24小时价格变动幅度小于指定百分比的加密货币。例如：-5，表示今天价格下跌超过 5% 的加密货币。
     */
    override suspend fun getCoins(
        page: Int,
        limit: Int,
        sortBy: String,
        sortDir: String,
        rankLessThan: Int,
        priceChange1dGreaterThan: Float?,
        priceChange1dLessThan: Float?,
    ): NetworkPageData<MarketsCoins> {
         return marketService.getCoins(page, limit,sortBy,sortDir,rankLessThan,priceChange1dGreaterThan,priceChange1dLessThan)
    }

    /**
     *  获取交易所列表
     */
    override suspend fun getTickersExchanges(): List<TickersExchanges> {
        return marketService.getTickersExchanges()
    }

    /**
     *  获取加密货币类别
     */
    override suspend fun getCoinsCategories(): List<MarketsCategories> {
        return marketService.getCoinsCategories()
    }

}
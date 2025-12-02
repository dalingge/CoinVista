package com.dalingge.coinvista.core.data.repository

import com.dalingge.coinvista.core.model.entity.MarketsCap
import com.dalingge.coinvista.core.model.entity.MarketsCoins
import com.dalingge.coinvista.core.model.entity.TickersExchanges
import com.dalingge.coinvista.core.model.response.NetworkPageData
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
    private val marketNetworkDataSource: MarketNetworkDataSource,
) {

    /**
     * 市值
     */
    fun getMarketsCap(): Flow<MarketsCap> =
        flow {
            emit(marketNetworkDataSource.getMarketsCap())
        }.flowOn(Dispatchers.IO)


    /**
     *  加密货币所有数据
     *
     *  @param page 要检索的页码（从 1 开始索引）
     *  @param type 0 排行榜 3 涨幅榜 4 跌幅榜
     */
    fun getCoins(
        page: Int,
        type: Int,
    ): Flow<NetworkPageData<MarketsCoins>> =
        flow {
            val sortBy = if (type == 0) "rank" else "priceChange1d"
            val sortDir = if (type == 0 || type == 4) "asc" else "desc"
            val priceChange1dGreaterThan = if (type == 3) 0f else null
            val priceChange1dLessThan = if (type == 4) 0f else null
            emit(marketNetworkDataSource.getCoins(page, 20, sortBy, sortDir, 300, priceChange1dGreaterThan, priceChange1dLessThan))
        }.flowOn(Dispatchers.IO)



    /**
     * 获取交易所列表
     */
    fun getTickersExchanges(): Flow<List<TickersExchanges>> =
        flow {
            emit(marketNetworkDataSource.getTickersExchanges())
        }.flowOn(Dispatchers.IO)

}



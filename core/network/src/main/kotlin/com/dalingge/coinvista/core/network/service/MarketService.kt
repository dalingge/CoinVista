package com.dalingge.coinvista.core.network.service

import com.dalingge.coinvista.core.model.entity.MarketsCap
import com.dalingge.coinvista.core.model.entity.MarketsCategories
import com.dalingge.coinvista.core.model.entity.MarketsCoins
import com.dalingge.coinvista.core.model.entity.SearchList
import com.dalingge.coinvista.core.model.entity.TickersExchanges
import com.dalingge.coinvista.core.model.response.NetworkPageData
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 *
 * @Description : 行情相关接口
 * @Author :Dalingge
 * @Time :2025/10/21  10:25
 */
interface MarketService {

    /**
     * 市值
     */
    @GET("/markets")
    suspend fun getMarketsCap(): MarketsCap

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
    @GET("/coins")
    suspend fun getCoins(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sortBy") sortBy: String,
        @Query("sortDir") sortDir: String,
        @Query("rank~lessThan") rankLessThan: Int,
        @Query("priceChange1d~greaterThan") priceChange1dGreaterThan: Float? = null,
        @Query("priceChange1d~lessThan") priceChange1dLessThan: Float? = null,
    ): NetworkPageData<MarketsCoins>

    /**
     * 获取交易所列表
     */
    @GET("/tickers/exchanges")
    suspend fun getTickersExchanges(): List<TickersExchanges>


    /**
     * 获取加密货币类别
     */
    @GET
    suspend fun getCoinsCategories(@Url url: String = "https://api.coin-stats.com/v5/coins/categories/list"): List<MarketsCategories>


    /**
     * 获取加密货币类别列表
     */
    @GET
    suspend fun getCoinsCategoriesSingle(
        @Url url: String = "https://api.coin-stats.com/v5/coins/categories/single",
        @Query("id") id: String,
        @Query("skip") page: Int,
        @Query("limit") limit: Int,
        @Query("sortBy") sortBy: String,
    ): List<TickersExchanges>


    /**
     *  热门搜索
     */
    @GET
    suspend fun searchTrendingCoins(
        @Url url: String = "https://api.coin-stats.com/v2/trending/search",
    ): List<SearchList>


    /**
     *  搜索
     *
     *  @param query 模糊搜索
     */
    @GET
    suspend fun searchCoins(
        @Url url: String = "https://api.coin-stats.com/v2/search",
        @Query("query") query: String,
    ): List<SearchList>

}
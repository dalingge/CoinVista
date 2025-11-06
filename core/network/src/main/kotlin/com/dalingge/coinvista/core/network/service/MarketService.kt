package com.dalingge.coinvista.core.network.service

import com.dalingge.coinvista.core.model.entity.MarketsCap
import retrofit2.http.GET


/**
 *
 * @Description : 行情相关接口
 * @Author :丁博洋
 * @Time :2025/10/21  10:25
 */
interface MarketService {

    /**
     * 市值
     */
    @GET("/markets")
    suspend fun getMarketsCap(): MarketsCap

}
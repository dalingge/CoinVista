package com.dalingge.coinvista.core.network.service

import com.dalingge.coinvista.core.model.entity.FearGreed
import retrofit2.http.GET

/**
 *
 * @Description :市场洞察相关接口
 * @Author :丁博洋
 * @Time :2025/11/21  18:18
 */
interface InsightsService {

    /**
     * 获取加密货币恐惧与贪婪指数
     */
    @GET("/insights/fear-and-greed")
    suspend fun getFearAndGreed(): FearGreed
}
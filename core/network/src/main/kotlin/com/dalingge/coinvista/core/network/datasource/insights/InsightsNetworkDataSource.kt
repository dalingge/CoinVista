package com.dalingge.coinvista.core.network.datasource.insights

import com.dalingge.coinvista.core.model.entity.FearGreed


/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/11/21  18:28
 */
interface InsightsNetworkDataSource{

    /**
     * 获取加密货币恐惧与贪婪指数
     */
    suspend fun getFearAndGreed(): FearGreed
}
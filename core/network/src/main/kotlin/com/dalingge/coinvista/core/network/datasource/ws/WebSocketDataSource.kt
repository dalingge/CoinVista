package com.dalingge.coinvista.core.network.datasource.ws

import com.dalingge.coinvista.core.model.request.KLine
import com.dalingge.coinvista.core.model.request.Ticker
import kotlinx.coroutines.flow.Flow

interface WebSocketDataSource {

    /**
     * 发送消息订阅
     */
    fun subscribe(id: Int, args: List<String>):Boolean

    /**
     * 取消订阅
     */
    fun unsubscribe(id: Int): Boolean

    /**
     * 全部订阅取消
     */
    fun unsubscribeAll()

    /**
     * 交易行情处理器
     */
    fun getTickerFlow(): Flow<Ticker>

    /**
     * 交易行情图处理器
     */
     fun getKLineFlow():  Flow<KLine>
}
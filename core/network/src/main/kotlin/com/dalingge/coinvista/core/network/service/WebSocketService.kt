package com.dalingge.coinvista.core.network.service

import com.dalingge.coinvista.core.model.request.KLine
import com.dalingge.coinvista.core.model.request.Subscribe
import com.dalingge.coinvista.core.model.request.Ticker
import com.dalingge.coinvista.core.model.response.WebsocketResponse
import com.tinder.scarlet.Event
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface WebSocketService {

    /**
     * 发送文本消息
     */
    @Send
    fun sendText(message: String): Boolean

    /**
     * 发送行情和k线消息
     */
    @Send
    fun sendSubscribe(subscribe: Subscribe):Boolean

    /**
     *
     */
    @Receive
    fun observeWebSocketEvent(): Flow<Event>

    /**
     * 行情数据监听
     */
    @Receive
    fun observeTicker(): Flow<Ticker>

    /**
     *  K线数据监听
     */
    @Receive
    fun observeKLine(): Flow<KLine>

    /**
     *  订阅结果监听
     */
    @Receive
    fun observeWsResult(): Flow<WebsocketResponse>

    /**
     *  文本消息监听
     */
    @Receive
    fun observeText(): Flow<String>
}
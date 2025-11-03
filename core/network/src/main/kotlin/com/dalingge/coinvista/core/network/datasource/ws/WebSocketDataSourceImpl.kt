package com.dalingge.coinvista.core.network.datasource.ws

import android.util.Log
import com.dalingge.coinvista.core.model.request.KLine
import com.dalingge.coinvista.core.model.request.Subscribe
import com.dalingge.coinvista.core.model.request.Ticker
import com.dalingge.coinvista.core.network.service.WebSocketService
import com.tinder.scarlet.Event
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.State
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class WebSocketDataSourceImpl(
    private val webSocketService: WebSocketService,
    private val coroutineScope: CoroutineScope,
    private val debug: Boolean,
) : WebSocketDataSource {

    private val debugMode = true

    private val subscribeMap = ConcurrentHashMap<Int, Subscribe>()
    private val subscribeStatusMap = ConcurrentHashMap<Int, Boolean>()

    private var lastReceivedTime = System.currentTimeMillis()

    private val _transactionTickerProcessor = MutableStateFlow(Ticker())

    private val _transactionKLineProcessor = MutableStateFlow(KLine())

    private val _connectionStatus = MutableStateFlow(false)  // 默认连接状态为 false（未连接）
    val connectionStatus: StateFlow<Boolean> = _connectionStatus  // 可公开的连接状态

    private val _subscribeStatus = MutableStateFlow(Pair(-1, false))  // 默认订阅接状态 false（未状态）
    val subscribeStatus: StateFlow<Pair<Int, Boolean>> = _subscribeStatus  // 可公开的订阅状态

    init {
        log("init")
        webSocketService.observeWebSocketEvent()
            .filter { if (it is Event.OnWebSocket.Event<*>) it.event !is WebSocket.Event.OnMessageReceived else false }
            .onEach { event ->
                val description = when (event) {
                    is Event.OnLifecycle.Terminate -> "\uD83D\uDCA5 On Lifecycle Terminate"
                    is Event.OnLifecycle.StateChange<*> -> when (event.state) {
                        is Lifecycle.State.Started -> "\uD83C\uDF1D On Lifecycle Start"
                        is Lifecycle.State.Stopped -> "\uD83C\uDF1A On Lifecycle Stop"
                        is Lifecycle.State.Destroyed -> "\uD83D\uDCA5 On Lifecycle Terminate"
                    }

                    is Event.OnWebSocket.Terminate -> "\uD83D\uDEF0️ On WebSocket Terminate"
                    is Event.OnWebSocket.Event<*> -> when (event.event) {
                        is WebSocket.Event.OnConnectionOpened<*> -> "\uD83D\uDEF0️ On WebSocket Connection Opened"
                        is WebSocket.Event.OnMessageReceived -> "\uD83D\uDEF0️ On WebSocket Message Received"
                        is WebSocket.Event.OnConnectionClosing -> "\uD83D\uDEF0️ On WebSocket Connection Closing"
                        is WebSocket.Event.OnConnectionClosed -> "\uD83D\uDEF0️ On WebSocket Connection Closed"
                        is WebSocket.Event.OnConnectionFailed -> "\uD83D\uDEF0️ On WebSocket Connection Failed"
                    }

                    is Event.OnStateChange<*> -> when (event.state) {
                        is State.WaitingToRetry -> "\uD83D\uDCA4 WaitingToRetry"
                        is State.Connecting -> "⏳ Connecting"
                        is State.Connected -> "\uD83D\uDEEB Connected"
                        is State.Disconnecting -> "⏳ Disconnecting"
                        is State.Disconnected -> "\uD83D\uDEEC Disconnected"
                        is State.Destroyed -> "\uD83D\uDCA5 Destroyed"
                    }

                    is Event.OnRetry -> "⏰ On Retry"
                }
                log("observe <- $description")
                if (event is Event.OnWebSocket.Event<*>) {
                    when (event.event) {
                        is WebSocket.Event.OnConnectionOpened<*> -> _connectionStatus.value = true
                        is WebSocket.Event.OnConnectionClosed -> _connectionStatus.value = false
                        is WebSocket.Event.OnConnectionFailed -> _connectionStatus.value = false
                        else -> {}
                    }
                }
                if (webSocketService.sendText("ping")) {
                    log("send -> ping")
                }

            }
            .catch {
                _connectionStatus.value = false
                log("error ->" + it.message)
            }
            .launchIn(coroutineScope)

        webSocketService.observeText()
            .filter { it == "pong" || it == "ping" }
            .onEach {
                log("observe <- $it")
                if (it == "ping") {
                    if (webSocketService.sendText("pong")) {
                        log("send -> pong")
                    }
                } else {
                    delay(5000)
                    if (webSocketService.sendText("ping")) {
                        log("send -> ping")
                    }
                }
            }
            .catch {
                log("error ->" + it.message)
            }
            .launchIn(coroutineScope)

        webSocketService.observeTicker()
            .filter { it.messageCode == 1 }
            .onEach {
                log("observe <- $it")
                lastReceivedTime = System.currentTimeMillis() // 更新接收到消息的时间
                _transactionTickerProcessor.value = it
            }
            .catch {
                log("error ->" + it.message)
            }
            .launchIn(coroutineScope)

        webSocketService.observeKLine()
            .filter { it.messageCode == 5 }
            .onEach {
                log("observe <- $it")
                _transactionKLineProcessor.value = it
            }
            .catch {
                log("error ->" + it.message)
            }
            .launchIn(coroutineScope)

        webSocketService.observeWsResult()
            .filter { it.id > -1 }
            .onEach {
                log("observe <- subscribe $it")
                //  _subscribeStatus.value = Pair(it.id, subscribeMap.containsKey(it.id))
            }
            .catch {
                // _subscribeStatus.value = Pair(it.d, subscribeStatusMap.containsKey(it.id))
                log("error ->" + it.message)
            }
            .launchIn(coroutineScope)

        // observeSubscribeStatus()
        observeConnectionStatus()
        coroutineScope.launch {
            monitorWebSocketConnection()
        }
    }

    override fun getTickerFlow(): Flow<Ticker> {
        return _transactionTickerProcessor.asStateFlow()
    }

    override fun getKLineFlow(): Flow<KLine> {
        return _transactionKLineProcessor.asStateFlow()
    }

    override fun subscribe(id: Int, args: List<String>): Boolean {
        val subscribe = Subscribe(
            id = id,
            method = "SUBSCRIBE",
            args = args
        )
        subscribeMap[id] = subscribe
        if (webSocketService.sendSubscribe(subscribe)) {
            log("send -> Success $subscribe")
            return true
        } else {
            //  _subscribeStatus.value = Pair(id, false)
            log("send -> Failed $subscribe")
            return false
        }
    }

    override fun unsubscribe(id: Int): Boolean {
        val subscribe = subscribeMap[id]
        val unsubscribe = Subscribe(
            id = id,
            method = "UNSUBSCRIBE",
            args = subscribe?.args ?: emptyList()
        )
        if (webSocketService.sendSubscribe(unsubscribe)) {
            subscribeMap.remove(id)
            //  _subscribeStatus.value = Pair(id, false)
            log("send -> Success $unsubscribe")
            return true
        } else {
            log("send -> Failed $unsubscribe")
            return false
        }
    }

    override fun unsubscribeAll() {
        subscribeMap.forEach {
            unsubscribe(it.key)
        }
    }

    private fun observeSubscribeStatus() {
        // 使用 Flow 监听订阅状态
        subscribeStatus.onEach { pair ->
            subscribeStatusMap[pair.first] = pair.second
        }.launchIn(coroutineScope)
    }

    /**
     *  监听连接状态变化
     */
    private fun observeConnectionStatus() {
        // 使用 Flow 监听连接状态
        connectionStatus.onEach { isConnected ->
            if (isConnected) {
                log("send -> Status Connection successful")
                // 连接成功，重新发送数
                reconnect()
            } else {
                log("send -> Status Connection Failed")
                // 连接失败时的处理
            }
        }.launchIn(coroutineScope)
    }

    private fun reconnect() {
        subscribeMap.forEach {
            log("send -> Status retry Subscribe ${it.value}")
            webSocketService.sendSubscribe(it.value)
        }
    }

    private suspend fun monitorWebSocketConnection() {
        // 每 5 秒检查一次
        while (true) {
            delay(5000)
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastReceivedTime > 5000 && subscribeMap.isNotEmpty() && _connectionStatus.value) {  // 超过 1 秒没有收到新数据
                log("No new data for 5 second, reconnecting...")
                reconnect()
            }
        }
    }

    private fun log(msg: String) {
        if (debugMode && debug) {
            Log.d("WebSocketService", msg)
        }
    }
}
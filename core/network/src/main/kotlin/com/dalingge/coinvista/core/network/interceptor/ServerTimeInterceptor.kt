package com.dalingge.coinvista.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class ServerTimeInterceptor : Interceptor {

    // HTTP 标准日期格式 RFC 7231
    // 使用 ThreadLocal 解决 SimpleDateFormat 在多线程下的线程安全问题，且避免频繁创建对象
    private val dateFormat = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("GMT")
            }
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        // 记录请求发出的时间（用于粗略计算网络延迟补偿）
        val requestTime = System.currentTimeMillis()
        
        val response = chain.proceed(chain.request())

        // 提取服务器 Date Header
        val dateHeader = response.header("Date")
        
        if (!dateHeader.isNullOrEmpty()) {
            try {
                val serverDate = dateFormat.get()?.parse(dateHeader)
                if (serverDate != null) {
                    val responseTime = System.currentTimeMillis()
                    // 网络请求在路上花费了时间。
                    // 假设往返耗时是对称的，单程耗时 = (响应到达时间 - 请求发出时间) / 2
                    val networkLatency = (responseTime - requestTime) / 2
                    
                    // 最终精准服务器时间 = Header时间 + 网络单程传输延迟
                    val exactServerTime = serverDate.time + networkLatency
                    
                    // 同步到全局管理器
                    ServerTimeManager.sync(exactServerTime)
                }
            } catch (e: Exception) {
                // 解析异常，通常是服务器返回了非标准的 Date 格式
                e.printStackTrace()
            }
        }

        return response
    }
}
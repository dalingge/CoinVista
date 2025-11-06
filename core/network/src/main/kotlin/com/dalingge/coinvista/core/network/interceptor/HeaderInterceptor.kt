package com.dalingge.coinvista.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2024/10/9  15:25
 */
class HeaderInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .header("X-API-KEY","wp9rctIPkII1ykYPj2JOFbBm9fBrnmim6NNqQzQJTQQ=")
            .build()

        return chain.proceed(request)
    }
}
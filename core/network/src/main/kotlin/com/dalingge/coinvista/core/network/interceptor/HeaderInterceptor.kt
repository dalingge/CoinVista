package com.dalingge.coinvista.core.network.interceptor

import com.dalingge.coinvista.core.network.BuildConfig
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
            .header("X-API-KEY", BuildConfig.API_KEY) //TODO: get your API Key https://openapi.coinstats.app/login
            .build()

        return chain.proceed(request)
    }
}
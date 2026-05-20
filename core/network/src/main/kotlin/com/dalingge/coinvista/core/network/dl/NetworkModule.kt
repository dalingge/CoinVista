package com.dalingge.coinvista.core.network.dl


import android.app.Application
import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dalingge.coinvista.core.network.BuildConfig
import com.dalingge.coinvista.core.network.adapter.KotlinxMessageAdapter
import com.dalingge.coinvista.core.network.adapter.StringMessageAdapter
import com.dalingge.coinvista.core.network.coroutines.CoroutinesStreamAdapterFactory
import com.dalingge.coinvista.core.network.datasource.insights.InsightsNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.insights.InsightsNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.datasource.market.MarketNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.market.MarketNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.datasource.news.NewsNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.news.NewsNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.datasource.nft.NFTsNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.nft.NFTsNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.datasource.trading.TradingNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.trading.TradingNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.datasource.user.UserNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.user.UserNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.interceptor.HeaderInterceptor
import com.dalingge.coinvista.core.network.interceptor.ServerTimeInterceptor
import com.dalingge.coinvista.core.network.multibaseurls.enableMultiBaseUrls
import com.dalingge.coinvista.core.network.service.InsightsService
import com.dalingge.coinvista.core.network.service.MarketService
import com.dalingge.coinvista.core.network.service.NFTsService
import com.dalingge.coinvista.core.network.service.NewsService
import com.dalingge.coinvista.core.network.service.TradingService
import com.dalingge.coinvista.core.network.service.UserService
import com.dalingge.coinvista.core.network.service.WebSocketService
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import org.koin.plugin.module.dsl.create
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit


/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2022/8/4  15:11
 */


val networkModule = module {

    single { create(::json) }
    single { create(::okHttp) }
    single { create(::retrofit) }
    
    single { create(::scarlet) }
    single { create(::webSocketService) }

    single { create(::marketService) }
    single { create(::insightsService) }
    single { create(::nftsService) }
    single { create(::tradingService) }
    single { create(::userService) }
    single { create(::newsService) }

    single<MarketNetworkDataSourceImpl>() bind MarketNetworkDataSource::class
    single<InsightsNetworkDataSourceImpl>() bind InsightsNetworkDataSource::class
    single<NFTsNetworkDataSourceImpl>() bind NFTsNetworkDataSource::class
    single<TradingNetworkDataSourceImpl>() bind TradingNetworkDataSource::class
    single<UserNetworkDataSourceImpl>() bind UserNetworkDataSource::class
    single<NewsNetworkDataSourceImpl>() bind NewsNetworkDataSource::class

}

private fun json(): Json = Json {
    //  prettyPrint = true        // 美化输出，便于阅读
    ignoreUnknownKeys = true  // 忽略未知字段
    isLenient = true         // 宽松模式
    //   encodeDefaults = true    // 编码默认值
    coerceInputValues = true // 强制输入值（如null转默认值）
}

private fun okHttp(context: Context): OkHttpClient = OkHttpClient.Builder()
    .cache(Cache(File(context.cacheDir, "okhttp"), 100 * 1024 * 1024))
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .enableMultiBaseUrls()
    .addInterceptor(HeaderInterceptor())
    .addInterceptor(ServerTimeInterceptor())
    .apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .alwaysReadResponseBody(true)
                    .build()
            )
            addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }
    .retryOnConnectionFailure(true)
    .proxy(if (BuildConfig.DEBUG) null else Proxy.NO_PROXY)
    .hostnameVerifier { _, _ -> true } //忽略host验证
    .build()

private fun retrofit(client: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
    .baseUrl("https://openapiv1.coinstats.app/")
    .client(client)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

private fun scarlet(context: Application, json: Json):Scarlet {
    val client = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .pingInterval(10, TimeUnit.SECONDS)
        .build()

   return Scarlet.Builder()
        .webSocketFactory(client.newWebSocketFactory("wss://push.stellaforex.com/stream"))
        .lifecycle(AndroidLifecycle.ofApplicationForeground(context))
        .backoffStrategy(LinearBackoffStrategy(2000))
        .addMessageAdapterFactory(StringMessageAdapter.Factory())
        .addMessageAdapterFactory(KotlinxMessageAdapter.Factory(json))
        .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
        .build()
}

private fun webSocketService(scarlet: Scarlet): WebSocketService = scarlet.create(WebSocketService::class.java)


private fun marketService(retrofit: Retrofit): MarketService = retrofit.create(MarketService::class.java)
private fun insightsService(retrofit: Retrofit): InsightsService = retrofit.create(InsightsService::class.java)
private fun nftsService(retrofit: Retrofit): NFTsService = retrofit.create(NFTsService::class.java)
private fun tradingService(retrofit: Retrofit): TradingService = retrofit.create(TradingService::class.java)
private fun userService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)
private fun newsService(retrofit: Retrofit): NewsService = retrofit.create(NewsService::class.java)

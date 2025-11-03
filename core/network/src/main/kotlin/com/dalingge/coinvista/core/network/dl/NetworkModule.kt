package com.dalingge.coinvista.core.network.dl

import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dalingge.coinvista.core.network.datasource.market.MarketNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.market.MarketNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.datasource.news.NewsNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.news.NewsNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.datasource.trading.TradingNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.trading.TradingNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.datasource.user.UserNetworkDataSource
import com.dalingge.coinvista.core.network.datasource.user.UserNetworkDataSourceImpl
import com.dalingge.coinvista.core.network.interceptor.HeaderInterceptor
import com.dalingge.coinvista.core.network.multibaseurls.enableMultiBaseUrls
import com.dalingge.coinvista.core.network.service.MarketService
import com.dalingge.coinvista.core.network.service.NewsService
import com.dalingge.coinvista.core.network.service.TradingService
import com.dalingge.coinvista.core.network.service.UserService
import com.tinder.scarlet.lifecycle.android.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit


/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2022/8/4  15:11
 */

val networkModule = module {

    single {
        Json {
            //  prettyPrint = true        // 美化输出，便于阅读
            ignoreUnknownKeys = true  // 忽略未知字段
            isLenient = true         // 宽松模式
            //   encodeDefaults = true    // 编码默认值
            coerceInputValues = true // 强制输入值（如null转默认值）
        }
    }

    single {
        OkHttpClient.Builder()
            .cache(Cache(File(androidApplication().cacheDir, "okhttp"), 100 * 1024 * 1024))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .enableMultiBaseUrls()
            .addInterceptor(HeaderInterceptor())
//            .addInterceptor(SubdomainInterceptor(BuildConfig.FLAVOR))
            .apply {
                if (BuildConfig.DEBUG) {
                    val chucker = ChuckerInterceptor.Builder(androidApplication())
//                .addBodyDecoder(object : BodyDecoder {
//                    override fun decodeRequest(request: Request, body: ByteString): String {
//                        val onResultDecoder = "false" != request.header(Param.DATA_DECRYPT)
//                        val result = body.string(UTF_8)
//                        return if (onResultDecoder){
//                            String(result.decodeHex().desDecrypt(GlobalData.key, GlobalData.iv))
//                        }else{
//                            result
//                        }
//                    }
//
//                    override fun decodeResponse(response: Response, body: ByteString): String {
//                        val onResultDecoder = OkHttpCompat.needDecodeResult(response)
//                        val result = body.string(UTF_8)
//                        return if (onResultDecoder){
//                            String(result.decodeHex().desDecrypt(GlobalData.key, GlobalData.iv))
//                        }else{
//                            result
//                        }
//                    }
//                })
                        .alwaysReadResponseBody(true)
                        .build()

                    addInterceptor(chucker)
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .retryOnConnectionFailure(true)
            .proxy(if (BuildConfig.DEBUG) null else Proxy.NO_PROXY)
            .hostnameVerifier { _, _ -> true } //忽略host验证
            .build()
    }

    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://hqdev.stellaforex.com/")
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory(contentType))
            .build()
    }


//    single {
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(HeaderInterceptor())
//            .pingInterval(10, TimeUnit.SECONDS)
//            .build()
//
//        val scarlet = Scarlet.Builder()
//            .webSocketFactory(client.newWebSocketFactory("wss://push.stellaforex.com/stream"))
//            .lifecycle( AndroidLifecycle.ofApplicationForeground(get()))
//            .backoffStrategy(LinearBackoffStrategy(2000))
//            .addMessageAdapterFactory(StringMessageAdapter.Factory())
//            .addMessageAdapterFactory(KotlinxMessageAdapter.Factory(get()))
//            .addStreamAdapterFactory(CoroutinesStreamAdapterFactory())
//            .build()
//        scarlet.create<WebSocketService>()
//    }

    single {
        ImageLoader.Builder(androidApplication())
            .crossfade(true)
            .logger(if (BuildConfig.DEBUG) DebugLogger() else null)
//            .components {
//                if (Build.VERSION.SDK_INT >= 28) {
//                    add(AnimatedImageDecoder.Factory())
//                } else {
//                    add(GifDecoder.Factory())
//                }
//            }
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(androidApplication(), 0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(androidApplication().cacheDir.resolve("image_cache"))
                    .maxSizeBytes(100L * 1024 * 1024)
                    .build()
            }
            .components {
                add(OkHttpNetworkFetcherFactory(get<OkHttpClient>().newBuilder().build()))
            }
            .build()
    }

    single { get<Retrofit>().create(MarketService::class.java) }
    single { get<Retrofit>().create(TradingService::class.java) }
    single { get<Retrofit>().create(UserService::class.java) }
    single { get<Retrofit>().create(NewsService::class.java) }

    single<MarketNetworkDataSource> { MarketNetworkDataSourceImpl(get()) }
    single<TradingNetworkDataSource> { TradingNetworkDataSourceImpl(get()) }
    single<UserNetworkDataSource> { UserNetworkDataSourceImpl(get()) }
    single<NewsNetworkDataSource> { NewsNetworkDataSourceImpl(get()) }


}

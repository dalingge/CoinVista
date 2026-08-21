package com.dalingge.coinvista

import android.app.Application
import android.content.res.Configuration
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.dalingge.coinvista.common.dl.commonModule
import com.dalingge.coinvista.core.data.di.appStateModule
import com.dalingge.coinvista.core.network.dl.networkModule
import com.dalingge.coinvista.main.dl.mainModule
import com.dalingge.coinvista.market.dl.marketModule
import com.dalingge.nav.generated.initCommon
import com.dalingge.nav.generated.initMain
import com.dalingge.nav.generated.initMarket
import com.dalingge.nav.runtime.DefaultSlideTransition
import com.dalingge.nav.runtime.NavCenter
import com.dalingge.nav.runtime.handler.BrowserHandler
import com.dalingge.nav.runtime.handler.WebViewHandler
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import java.net.Proxy

/**
 * 全局Application
 */
class Application : Application(), SingletonImageLoader.Factory {


    override fun onCreate() {
        super.onCreate()

        NavCenter
            .setFallbackRoute("app/not_found")
            .addRouteHandler(WebViewHandler("common/webview", setOf("github.com")))
            .addRouteHandler(BrowserHandler(this))
            .setDefaultTransition(DefaultSlideTransition()) // 注册全局动画
          //  .addGlobalInterceptor(AppLoginInterceptor())   // 注册全局拦截器
            .addEntryDecorator { rememberViewModelStoreNavEntryDecorator() }
           // .addEntryDecorator(AnalyticsEntryDecorator())//传入自定义的曝光埋点与 onPop 清理装饰器
            .initMain()
            .initMarket()
            .initCommon()

        val app = this
        val debugMode = BuildConfig.DEBUG
        startKoin {
            androidLogger(if (debugMode) Level.DEBUG else Level.NONE)
            androidContext(app)
            modules( networkModule, appStateModule, mainModule, marketModule, commonModule)
        }
    }

    /**
     * 应用配置变化时调用（如切换深色模式）
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // 检测深色模式变化并更新Toast样式
        val isDarkTheme = newConfig.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

        // 根据当前主题重新设置Toast样式
        if (isDarkTheme) {

        } else {

        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {

        val okhttp = OkHttpClient.Builder()
            .proxy(if (BuildConfig.DEBUG) null else Proxy.NO_PROXY)
            .build()

        return ImageLoader.Builder(context)
            .crossfade(true)
            .logger(if (BuildConfig.DEBUG) DebugLogger() else null)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("coil_image_cache"))
                    .maxSizeBytes(500L * 1024 * 1024)
                    .build()
            }
            .components {
                add(OkHttpNetworkFetcherFactory(okhttp))
            }
            .build()
    }
}

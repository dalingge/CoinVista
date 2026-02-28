package com.dalingge.coinvista

import android.app.Application
import android.content.res.Configuration
import com.dalingge.coinvista.common.dl.commonModule
import com.dalingge.coinvista.core.data.di.appStateModule
import com.dalingge.coinvista.core.network.dl.networkModule
import com.dalingge.coinvista.dl.appModule
import com.dalingge.coinvista.main.dl.mainModule
import com.dalingge.coinvista.market.dl.marketModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

/**
 * 全局Application
 */
class Application : Application() {


    override fun onCreate() {
        super.onCreate()

        val app = this
        val debugMode = BuildConfig.DEBUG
        startKoin {
            androidLogger(if (debugMode) Level.DEBUG else Level.NONE)
            androidContext(app)
            modules(appModule, networkModule,appStateModule,mainModule,marketModule,commonModule)
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
}
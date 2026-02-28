package com.dalingge.coinvista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.navigation.AppNavigator
import com.dalingge.coinvista.navigation.AppNavHost
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {

    val navigator: AppNavigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 启用边缘到边缘的显示效果
        enableEdgeToEdge()
        // 设置Compose内容
        setContent {
            // 应用主题包装
            AppTheme {
                // 设置应用的导航宿主，并传入导航管理器和路由注册器
                // 这样所有页面都可以通过导航管理器进行导航操作
                AppNavHost(navigator = navigator)
            }
        }
    }
}

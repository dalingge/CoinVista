package com.dalingge.coinvista

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.main.view.SplashRouteDestination
import com.dalingge.nav.runtime.NavCenter


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 启用边缘到边缘的显示效果
        enableEdgeToEdge()

        // 解耦恢复逻辑三部曲：
        // 优先尝试从进程被杀恢复 (savedInstanceState)
        val isRestored = NavCenter.restoreState(savedInstanceState)

        // 尝试从外部 Intent / DeepLink / 推送唤起
        val isIntentHandled = NavCenter.handleIntent(intent)

        // 若既没有进程恢复，也没有外部 DeepLink，则默认压入根首页
        if (!isRestored && !isIntentHandled && NavCenter.primaryStack.backstack.isEmpty()) {
            NavCenter.navigate(SplashRouteDestination())  //  压入根首页
        }

        // 设置Compose内容
        setContent {
            // 应用主题包装
            AppTheme {
                NavCenter.Render()
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            if (!NavCenter.pop()) {
                finish() }

        }
    }

    // 响应 Activity 在后台被杀死前的状态保存
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        NavCenter.saveState(outState) // 将当前导航栈持久化保存
    }

    //  响应 Activity 为 singleTop/singleTask 模式下的外部 Scheme / 推送新唤起
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        NavCenter.handleIntent(intent)
    }
}

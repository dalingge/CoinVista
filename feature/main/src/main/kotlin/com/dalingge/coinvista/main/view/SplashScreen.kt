package com.dalingge.coinvista.main.view

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.main.viewmodel.SplashViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * 启动页路由
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param viewModel 启动页 ViewModel
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SplashRoute(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel : SplashViewModel = koinViewModel()
) {
    SplashScreen(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        toHome = viewModel::toMainPage
    )
}

/**
 * 启动页界面
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param toHome 导航到主页的回调
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
internal fun SplashScreen(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    toHome: () -> Unit = {},
) {
    SplashContentView(
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        toHome = toHome
    )
}

/**
 * 启动页内容视图
 *
 * @param sharedTransitionScope 共享转换作用域
 * @param animatedContentScope 动画内容作用域
 * @param toHome 导航到主页的回调
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SplashContentView(
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedContentScope: AnimatedContentScope? = null,
    toHome: () -> Unit = {},
) {

    Column(modifier = Modifier.fillMaxSize()) {
//        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
//        // 如果选中，播放动画；如果未选中，不播放动画但保持在初始帧
//        val progress by animateLottieCompositionAsState(composition)
//
//        // 播放结束回调
//        LaunchedEffect(progress) {
//            if (progress >= 1f) {
//                toHome()
//            }
//        }
//
//        LottieAnimation(composition, progress = { progress })
    }
}


/**
 * 启动页界面浅色主题预览
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
internal fun SplashScreenPreview() {
    AppTheme {
        SplashScreen(
            sharedTransitionScope = null,
            animatedContentScope = null
        )
    }
}

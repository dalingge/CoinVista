package com.dalingge.coinvista.main.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.feature.main.R
import com.dalingge.coinvista.main.viewmodel.SplashViewModel
import com.dalingge.nav.annotation.Screen
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import kotlin.math.abs
import kotlin.time.Duration.Companion.milliseconds

/**
 * 启动页路由
 *
 * @param viewModel 启动页 ViewModel
 */

@Screen(route = "app/splash")
@Composable
internal fun SplashRoute(
    viewModel: SplashViewModel = koinViewModel(),
) {
    SplashScreen(
        toHome = viewModel::toMainPage
    )
}

/**
 * 启动页界面
 *
 * @param toHome 导航到主页的回调
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SplashScreen(toHome: () -> Unit = {}) {
    SplashContentView(toHome = toHome)
}

/**
 * 启动页内容视图
 *
 * @param toHome 导航到主页的回调
 */
@Composable
private fun SplashContentView(
    toHome: () -> Unit = {},
) {
    // --- 1. 动画状态管理 (保持不变) ---

    // Logo 上下跳动动画
    val infiniteTransition = rememberInfiniteTransition(label = "JumpAnim")

    // 位移：0dp -> -30dp (向上跳)
    val dy by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "TranslationY"
    )

    // 阴影缩放：Logo 越高，阴影越小、越淡
    val shadowScale = 1.0f - (abs(dy) / 30f) * 0.4f
    val shadowAlpha = 0.3f - (abs(dy) / 30f) * 0.2f

    // 文字打印机进度 (0f -> 1f)
    val textAnim = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(500.milliseconds)
        textAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutLinearInEasing)
        )
        toHome()
    }

    // --- 2. 界面布局 ---
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // A. Logo 区域
            Box(contentAlignment = Alignment.Center) {
                // 1. 底部阴影 (Shadow)
                Canvas(
                    modifier = Modifier
                        .size(60.dp, 10.dp)
                        .offset(y = 50.dp) // 放在 Logo 下方
                        .scale(shadowScale)
                        .alpha(shadowAlpha)
                ) {
                    drawOval(color = Color.Gray)
                }

                // 2. Logo 图片 (上下跳动)
                Box(modifier = Modifier.offset(y = dy.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(100.dp) // 设置图片大小
                            .clip(RoundedCornerShape(20.dp)) // 如果图片是方的，加上圆角让它更好看
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // B. 打印机效果文字
            TypewriterText(
                text = "Coin Vista",
                progress = textAnim.value
            )
        }
    }
}


/**
 * 打印机文字组件：
 * 前半部分黑色，后半部分灰色
 */
@Composable
fun TypewriterText(text: String, progress: Float) {
    val textLength = text.length
    val blackCharCount = (textLength * progress).toInt().coerceIn(0, textLength)

    Text(
        text = buildAnnotatedString {
            // 已打印部分 (黑色)
            withStyle(style = SpanStyle(color = Color.Black)) {
                append(text.substring(0, blackCharCount))
            }
            // 未打印部分 (灰色)
            withStyle(style = SpanStyle(color = Color.LightGray)) {
                append(text.substring(blackCharCount, textLength))
            }
        },
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp
    )
}

/**
 * 启动页界面浅色主题预览
 */
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    AppTheme {
        SplashScreen()
    }
}

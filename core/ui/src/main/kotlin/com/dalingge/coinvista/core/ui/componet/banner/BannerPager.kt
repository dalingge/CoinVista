package com.dalingge.coinvista.core.ui.componet.banner

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/17  15:54
 */

/**
 * BannerPager - 无限循环 + 自动轮播 + 指示器
 *
 * @param items 要展示的数据列表
 * @param modifier Compose Modifier
 * @param interval 自动轮播间隔（毫秒）。设为 <=0 则不自动播放
 * @param content 每页的显示内容（接收 item）
 */
@Composable
fun <T> BannerPager(
    items: List<T>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),
    pageSpacing: Dp = 0.dp,
    autoplay: Boolean = true,
    interval: Long = 3000L,
    indicator: (@Composable BoxScope.(count: Int, current: Int) -> Unit)? = { count, current ->
        BannerDefaults.Indicator(
            count,
            current,
            Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-10).dp)
        )
    },
    content: @Composable PagerScope.(item: T, pageIndex: Int) -> Unit,
) {
    if (items.isEmpty()) return

    // 为了无限循环，我们把初始页设到一个很大的数中间
    val startIndex = Int.MAX_VALUE / 2
    // 保证 startIndex 能被 items.size 整除，避免偏移
    val initialPage = startIndex - (startIndex % items.size)
    val pagerState = rememberPagerState(initialPage) {
        Int.MAX_VALUE // 虚拟无限页
    }

    // 自动播放的协程
    if (autoplay) {
        AutoScrollEffect(
            pagerState = pagerState,
            autoScrollDelayMillis = interval
        )
    }

    Box {
        HorizontalPager(
            state = pagerState,
            modifier = modifier,
            contentPadding = contentPadding,
            pageSpacing = pageSpacing,
            pageSize = PageSize.Fill
        ) { page ->
            // 将虚拟 page 转换成 items 索引
            val index = ((page % items.size) + items.size) % items.size
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                content(items[index], index)
            }
        }

        if (indicator != null) {
            indicator(items.size, pagerState.currentPage)
        }

    }
}


/**
 * 自动滚动效果：当用户没有手动滚动时，每隔 autoScrollDelayMillis 滚到下一页
 */
@Composable
private fun AutoScrollEffect(
    pagerState: PagerState,
    autoScrollDelayMillis: Long,
) {
    if (autoScrollDelayMillis <= 0L) return

    // 在 Inspection / Preview 模式下防止协程一直跑
    val isInPreview = LocalInspectionMode.current
    LaunchedEffect(key1 = pagerState, key2 = autoScrollDelayMillis, key3 = isInPreview) {
        if (isInPreview) return@LaunchedEffect
        while (true) {
            delay(autoScrollDelayMillis)
            // 如果用户正在拖动则跳过这次自动滚动
            if (!pagerState.isScrollInProgress) {
                val target = pagerState.currentPage + 1
                // 使用默认动画时长，如果需要可以调整 tween 的 durationMillis
                try {
                    pagerState.animateScrollToPage(target, animationSpec = tween(durationMillis = 400))
                } catch (_: Exception) {
                    // 当 Compose 状态被取消/Activity 销毁时可能抛异常，安全忽略
                }
            }
        }
    }
}

object BannerDefaults {
    @Composable
    fun Indicator(count: Int, current: Int, modifier: Modifier = Modifier) {

        val current = ((current % count) + count) % count
        Row(
            modifier = modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (i in 0..<count) {
                val isSelected = i == current
                Box(
                    modifier = Modifier
                        .size(if (isSelected) 10.dp else 8.dp)
                        .background(
                            color = if (isSelected) Color.White else Color.LightGray.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}

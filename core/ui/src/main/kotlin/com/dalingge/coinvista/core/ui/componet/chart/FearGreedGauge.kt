package com.dalingge.coinvista.core.ui.componet.chart

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dalingge.coinvista.core.design.theme.BodyMedium
import com.dalingge.coinvista.core.design.theme.DisplayMedium
import com.dalingge.coinvista.core.design.theme.TextPrimaryLight
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/11/24  14:00
 */

val ColorExtremeFear = Color(0xFFEB3333)
val ColorFear = Color(0xFFFF7F00)
val ColorGreed = Color(0xFFFFD700)
val ColorExtremeGreed = Color(0xFF00C087)
val ColorIndicatorBorder = Color.DarkGray
val ColorIndicatorFill = Color.White // 指针橙色

@Composable
fun FearGreedGauge(
    score: Int,
    modifier: Modifier = Modifier,
) {
    // 动画过渡分数
    val animatedScore by animateFloatAsState(
        targetValue = score.toFloat(),
        animationSpec = tween(durationMillis = 1000),
        label = "GaugeAnimation"
    )

    // 仪表盘配置
    val startAngle = 180f
    val sweepAngle = 180f
    val strokeWidth = 12f // 弧线的粗细

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(60.dp, 50.dp) // 限制画布高度
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // 半圆的半径 (基于宽度，减去笔触宽度的一半以防被切断)
            val radius = (width - strokeWidth) / 2
            // 圆心坐标 (底部居中)
            val center = Offset(x = width / 2, y = height - strokeWidth / 2)

            // 1. 绘制发光效果 (可选，利用 Paint 的 setShadowLayer)
            // 注意：在 Compose 中绘制发光比较消耗性能，这里用简单的重复绘制模拟或省略
            // 为了简单起见，这里直接绘制渐变弧

            // 2. 定义渐变笔刷 (红 -> 黄 -> 绿)
            val gradientBrush = Brush.horizontalGradient(
                colors = listOf(ColorExtremeFear, ColorFear, ColorGreed, ColorExtremeGreed),
                startX = 0f,
                endX = width
            )

            // 3. 绘制背景弧线
            drawArc(
                brush = gradientBrush,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // 4. 计算指针位置
            // 分数 0 -> 180度 (左边)
            // 分数 100 -> 0度 (右边)
            // 注意：Compose Canvas 0度是3点钟方向。180度是9点钟方向。
            // 我们的弧是从 180 (9点) 开始，扫过 180 到 0 (3点)。
            val normalizedScore = (animatedScore / 100f).coerceIn(0f, 1f)
            val angleInDegrees = 180f - (normalizedScore * 180f)
            val angleInRadians = Math.toRadians(angleInDegrees.toDouble())

            val indicatorX = center.x + radius * cos(angleInRadians).toFloat()
            val indicatorY = center.y - radius * sin(angleInRadians).toFloat()

            // 5. 绘制指针 (黑色边框 + 橙色填充)
            val indicatorRadius = 8f // 指针大小

            // 绘制指针黑色边框
            drawCircle(
                color = ColorIndicatorBorder,
                radius = indicatorRadius + 4f, // 边框厚度
                center = Offset(indicatorX, indicatorY)
            )
            // 绘制指针内部颜色
            drawCircle(
                color = ColorIndicatorFill,
                radius = indicatorRadius,
                center = Offset(indicatorX, indicatorY)
            )
        }

        // 中间的数字
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = score.toString(),
                modifier = modifier.padding(top = 20.dp),
                color = TextPrimaryLight,
                style = DisplayMedium,
            )
        }
    }

}

// 辅助函数：根据分数获取文字描述
fun getStatusText(score: Int): String {
    return when (score) {
        in 0..24 -> "极度恐惧"
        in 25..49 -> "恐惧"
        in 50..54 -> "中性"
        in 55..74 -> "贪婪"
        else -> "极度贪婪"
    }
}

@Preview
@Composable
fun PreviewGauge() {
    FearGreedGauge(score = 100)
}
package com.dalingge.coinvista.core.ui.componet.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.dalingge.coinvista.core.design.theme.MarkerGrayColor
import com.dalingge.coinvista.core.design.theme.MarkerGreenColor
import com.dalingge.coinvista.core.design.theme.MarkerRedColor
import com.dalingge.coinvista.core.design.theme.ShapeXSmall
import com.dalingge.coinvista.core.design.theme.robotoSansFamily
import kotlin.math.abs

/**
 *
 * @Description : 股票行情界面中，数字根据涨跌自动显示红绿颜色
 * @Author :丁博洋
 * @Time :2025/10/31  12:56
 */
@Composable
fun StockPriceText(
    price: Float,
    previousClose: Float,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign? = null,
    decimalPlaces: Int = 2, // ✅ 动态小数位数控制
    useChange: Boolean = false,  // 是否以百分比显示
    usePercent: Boolean = false, // 是否以百分比显示
    showSign: Boolean = false, // 是否显示 + 或 -
) {
    val change = price - previousClose

    val percent = if (previousClose != 0f) (change / previousClose * 100f) else 0f

    val color = when {
        change > 0 -> MarkerGreenColor    // 绿色 - 涨
        change < 0 -> MarkerRedColor      // 红色 - 跌
        else -> MarkerGrayColor           // 灰色 - 平
    }

    val formattedText = when {
        useChange -> {
            val sign = if (showSign && change > 0) "+" else ""
            "$sign${"%.${decimalPlaces}f".format(abs(change))}"
        }

        usePercent -> {
            val sign = if (showSign && change > 0) "+" else ""
            "$sign${"%.3f".format(abs(percent))}%"
        }

        else -> {
            val sign = if (showSign && change > 0) "+" else ""
            "$sign${"%.${decimalPlaces}f".format(price)}"
        }
    }

    Text(
        text = formattedText,
        color = color,
        modifier = modifier,
        fontSize = fontSize,
        fontFamily = robotoSansFamily,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = TextOverflow.Clip,
        maxLines = 1
    )
}

@Composable
fun StockPriceTag(
    price: Float,
    previousClose: Float,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Bold,
    textAlign: TextAlign? = null,
    decimalPlaces: Int = 2, // ✅ 动态小数位数控制
    useChange: Boolean = false,  // 是否以百分比显示
    usePercent: Boolean = false, // 是否以百分比显示
    showSign: Boolean = false, // 是否显示 + 或 -
){

    val change = price - previousClose

    val percent = if (previousClose != 0f) (change / previousClose * 100f) else 0f

    val color = when {
        change > 0 -> MarkerGreenColor    // 绿色 - 涨
        change < 0 -> MarkerRedColor      // 红色 - 跌
        else -> MarkerGrayColor           // 灰色 - 平
    }

    val formattedText = when {
        useChange -> {
            val sign = if (showSign && change > 0) "+" else ""
            "$sign${"%.${decimalPlaces}f".format(abs(change))}"
        }

        usePercent -> {
            val sign = if (showSign && change > 0) "+" else ""
            "$sign${"%.3f".format(abs(percent))}%"
        }

        else -> {
            val sign = if (showSign && change > 0) "+" else ""
            "$sign${"%.${decimalPlaces}f".format(price)}"
        }
    }

    Box(
        modifier = modifier
            .clip(ShapeXSmall)
            .background(color)
    ) {

        Text(
            text = formattedText,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
            fontSize = fontSize,
            fontFamily = robotoSansFamily,
            fontWeight = fontWeight,
            textAlign = textAlign,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
    }
}


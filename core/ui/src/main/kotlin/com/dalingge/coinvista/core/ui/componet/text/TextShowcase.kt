package com.dalingge.coinvista.core.ui.componet.text

import android.R.attr.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dalingge.coinvista.core.design.theme.AppTheme

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/31  14:29
 */
@Composable
fun TextShowcase() {

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            StockPriceText(109677.2f, 103392.2f)

            StockPriceText(109677.2f, 103392.2f, useChange = true)

            StockPriceText(109677.2f, 103392.2f, usePercent = true, showSign = true)
        }
    }
}


/**
 * 文本组件展示页面预览 - 浅色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun TextShowcasePreviewLight() {
    AppTheme(darkTheme = false) {
        TextShowcase()
    }
}

/**
 * 文本组件展示页面预览 - 深色主题
 *
 * @author Joker.X
 */
@Preview(showBackground = true)
@Composable
fun TextShowcasePreviewDark() {
    AppTheme(darkTheme = true) {
        TextShowcase()
    }
}
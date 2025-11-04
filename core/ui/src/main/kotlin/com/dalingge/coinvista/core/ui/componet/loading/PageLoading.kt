package com.dalingge.coinvista.core.ui.componet.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.design.theme.SpaceVerticalSmall

/**
 * 页面加载中
 * @param modifier 可选修饰符
 */
@Composable
fun PageLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WeLoading(40.dp)
        SpaceVerticalSmall()
        Text(text = "加载中...")
    }
}


@Preview(showBackground = true)
@Composable
fun PageLoadingPreview() {
    AppTheme {
        PageLoading()
    }
}
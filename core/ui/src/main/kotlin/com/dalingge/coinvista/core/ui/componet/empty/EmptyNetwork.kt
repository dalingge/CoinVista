package com.dalingge.coinvista.core.ui.componet.empty

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.ui.R

/**
 * 网络连接失败状态视图
 */
@Composable
fun EmptyNetwork(
    modifier: Modifier = Modifier,
    onRetryClick: (() -> Unit)? = null
) {
    Empty(
        modifier = modifier,
        message = R.string.empty_network,
        icon = R.drawable.ic_empty_data,
        retryButtonText = R.string.click_retry,
        onRetryClick = onRetryClick
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyNetworkPreview() {
    AppTheme {
        Empty(
            message = R.string.empty_network,
            icon = R.drawable.ic_empty_data,
            retryButtonText = R.string.click_retry,
        )
    }
}
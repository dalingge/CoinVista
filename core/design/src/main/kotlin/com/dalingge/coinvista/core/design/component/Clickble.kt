package com.dalingge.coinvista.core.design.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/10/29  18:35
 */

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: () -> Unit = {}
): Modifier = composed {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        enabled = enabled,
        onClick = onClick
    )
}
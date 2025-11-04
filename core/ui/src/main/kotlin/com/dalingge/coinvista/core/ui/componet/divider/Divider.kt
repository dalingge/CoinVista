package com.dalingge.coinvista.core.ui.componet.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divider(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.outline) {
    HorizontalDivider(modifier, thickness = 0.5.dp, color = color)
}
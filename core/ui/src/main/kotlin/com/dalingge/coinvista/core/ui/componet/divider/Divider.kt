package com.dalingge.coinvista.core.ui.componet.divider

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dalingge.coinvista.core.design.theme.SpaceDivider

@Composable
fun HDivider(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.outline) {
    HorizontalDivider(modifier, thickness = SpaceDivider, color = color)
}

@Composable
fun VDivider(modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.outline) {
    VerticalDivider(modifier, thickness = SpaceDivider, color = color)
}
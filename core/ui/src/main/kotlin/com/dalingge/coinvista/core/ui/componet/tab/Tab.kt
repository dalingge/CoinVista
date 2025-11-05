package com.dalingge.coinvista.core.ui.componet.tab

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dalingge.coinvista.core.design.theme.robotoSansFamily
import com.dalingge.coinvista.core.ui.componet.divider.VDivider

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/16  17:24
 */

@Composable
fun ScrollableTextTabComponent(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit = {  }
) {
    SecondaryScrollableTabRow(
        edgePadding = 0.dp,
        selectedTabIndex = selectedIndex,
        minTabWidth = 0.dp,
        divider = {  },
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedIndex),
                width = 32.dp,
                height = 2.5.dp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    ) {
        tabs.forEachIndexed { index, text ->
            Tab(
                modifier = Modifier.height(44.dp),
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = text,
                        fontSize = 14.sp,
                        fontFamily = robotoSansFamily,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedIndex == index)
                            MaterialTheme.colorScheme.primary
                        else
                            Color(0xFF9A999D)
                    )
                }
            )
        }
    }
}

@Preview
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_C)
@Composable
private fun ScrollableTextTabComponentPreview() {
    val tabTitles = listOf("Popular", "Crypto", "Index", "Commodity", "Forex")
    ScrollableTextTabComponent(tabTitles, 0)
}
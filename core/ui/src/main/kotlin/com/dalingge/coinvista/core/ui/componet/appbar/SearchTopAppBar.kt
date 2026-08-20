package com.dalingge.coinvista.core.ui.componet.appbar

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dalingge.coinvista.core.design.component.CenterRow
import com.dalingge.coinvista.core.design.theme.AppTheme
import com.dalingge.coinvista.core.design.theme.ArrowLeftIcon
import com.dalingge.coinvista.core.design.theme.BgContentLight
import com.dalingge.coinvista.core.design.theme.CommonIcon
import com.dalingge.coinvista.core.design.theme.ShapeMedium
import com.dalingge.coinvista.core.design.theme.SpaceHorizontalMedium
import com.dalingge.coinvista.core.design.theme.SpaceHorizontalSmall
import com.dalingge.coinvista.core.ui.R
import com.dalingge.coinvista.core.ui.componet.text.AppText
import com.dalingge.coinvista.core.ui.componet.text.TextType
import kotlinx.coroutines.delay
import org.koin.ext.clearQuotes
import kotlin.time.Duration.Companion.milliseconds

/**
 *
 * @Description : 搜索顶部栏
 * @param onBackClick 返回按钮点击回调
 * @param onSearch 搜索回调
 * @param initialSearchText 初始搜索文本
 * @param actions 右侧操作按钮
 * @param scrollBehavior 滚动行为
 * @Author : Dalingge
 * @Time :2025/12/8  13:52
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SearchTopAppBar(
    onBackClick: () -> Unit,
    onSearch: (String) -> Unit,
    initialSearchText: String = "",
    scrollBehavior: TopAppBarScrollBehavior? = null,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
    var searchText by rememberSaveable { mutableStateOf(initialSearchText) }
    val focusManager = LocalFocusManager.current

    val focusRequester = remember { FocusRequester() } //焦点
    val softKeyboard = LocalSoftwareKeyboardController.current //软键盘

    LaunchedEffect(Unit) {
        delay(100.milliseconds) //延迟操作(关键点)
        focusRequester.requestFocus()
        softKeyboard?.show()
    }

    val performSearch = {
        onSearch(searchText)
        focusManager.clearFocus()
    }

    TopAppBar(
        title = {
            CenterRow(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(ShapeMedium)
                    .background(BgContentLight)
                    .let {
                        if (sharedTransitionScope != null && animatedVisibilityScope != null) {
                            with(sharedTransitionScope) {
                                it.sharedElement(
                                    sharedContentState = rememberSharedContentState(key = "search_element"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                            }
                        } else {
                            it
                        }
                    },
            ) {

                SpaceHorizontalMedium()

                CommonIcon(
                    resId = R.drawable.ic_search,
                    size = 18.dp,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )

                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = SpaceHorizontalSmall),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = { performSearch() }
                    ),
                    decorationBox = { innerTextField ->
                        if (searchText.isEmpty()) {
                            AppText(
                                text = "币种、NFT、钱包、ENS",
                                type = TextType.TERTIARY
                            )
                        }
                        innerTextField()
                    }
                )

                if (searchText.isNotEmpty()) {
                    IconButton(onClick = { searchText = "" }) {
                        CommonIcon(
                            resId = R.drawable.ic_clear,
                            size = 18.dp,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        )
                    }
                }
            }
        },
        actions = {
            AppText("取消", modifier = Modifier.padding(10.dp).clickable{
                onBackClick()
            })
        },
        colors = TopAppBarDefaults.topAppBarColors(),
        scrollBehavior = scrollBehavior
    )
}

/**
 * 搜索顶部栏预览
 *
 * @author Joker.X
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun SearchTopAppBarPreview() {
    AppTheme {
        SearchTopAppBar(
            onSearch = {},
            onBackClick = {}
        )
    }
}
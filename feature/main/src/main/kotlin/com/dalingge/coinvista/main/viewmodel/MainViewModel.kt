package com.dalingge.coinvista.main.viewmodel

import com.dalingge.coinvista.core.common.base.viewmodel.BaseViewModel
import com.dalingge.coinvista.core.data.state.AppState
import com.dalingge.coinvista.navigation.AppNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/14  15:14
 */
class MainViewModel(
    navigator: AppNavigator,
    appState: AppState
) : BaseViewModel(navigator, appState) {



    // 当前页面索引
    private val _currentPageIndex = MutableStateFlow(0)
    val currentPageIndex: StateFlow<Int> = _currentPageIndex.asStateFlow()

    /**
     * 更新当前选中的导航项和页面索引
     */
    fun updateDestination(index: Int) {
        _currentPageIndex.value = index
    }

    /**
     * 根据页面索引更新导航项
     */
    fun updatePageIndex(index: Int) {
        _currentPageIndex.value = index
    }
}
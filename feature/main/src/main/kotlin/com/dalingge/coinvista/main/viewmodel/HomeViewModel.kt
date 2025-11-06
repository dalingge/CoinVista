package com.dalingge.coinvista.main.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import com.dalingge.coinvista.core.common.base.viewmodel.BaseNetWorkViewModel
import com.dalingge.coinvista.core.common.base.viewmodel.BaseViewModel
import com.dalingge.coinvista.core.data.repository.MarketRepository
import com.dalingge.coinvista.core.data.state.AppState
import com.dalingge.coinvista.core.model.entity.MarketsCap
import com.dalingge.coinvista.navigation.AppNavigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    navigator: AppNavigator,
    appState: AppState,
    private val marketRepository: MarketRepository,
) : BaseNetWorkViewModel<MarketsCap>(navigator, appState) ,DefaultLifecycleObserver{


    // 当前选中的标签索引
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex = _selectedTabIndex.asStateFlow()

    /**
     * 是否正在进行标签切换动画
     */
    private val _isAnimatingTabChange = MutableStateFlow(false)
    val isAnimatingTabChange: StateFlow<Boolean> = _isAnimatingTabChange.asStateFlow()

    init {
        executeRequest()
    }

    override fun requestApiFlow(): Flow<MarketsCap> {
        return marketRepository.getMarketsCap()
    }

    /**
     * 加载数据（如果未加载）
     */
    private fun loadTabDataIfNeeded(tabIndex: Int) {

    }

    /**
     * 通知标签切换动画已完成
     */
    fun notifyAnimationCompleted() {
        _isAnimatingTabChange.value = false
    }

    /**
     * 更新选中的标签
     */
    fun updateSelectedTab(index: Int) {
        if (_selectedTabIndex.value != index) {
            _selectedTabIndex.value = index
            _isAnimatingTabChange.value = true

            // 当切换到新标签页时，检查并按需加载数据
            loadTabDataIfNeeded(index)
        }
    }

    /**
     * 根据页面滑动更新选中的标签
     */
    fun updateTabByPage(index: Int) {
        if (_selectedTabIndex.value != index) {
            _selectedTabIndex.value = index
            loadTabDataIfNeeded(index)
        }
    }

    /**
     * 跳转到搜索页面
     */
    fun toSearch() {

    }

    /**
     * 跳转到 GitHub 页面
     */
    fun toGitHubPage() {
        val url = "https://github.com/Joker-x-dev/CoolMallKotlin"
        val title = "GitHub"
//        super.toPage(
//            "${CommonRoutes.WEB}?url=${
//                java.net.URLEncoder.encode(
//                    url,
//                    "UTF-8"
//                )
//            }&title=${java.net.URLEncoder.encode(title, "UTF-8")}"
//        )
    }
}
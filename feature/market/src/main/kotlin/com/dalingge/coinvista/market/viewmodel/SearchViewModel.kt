package com.dalingge.coinvista.market.viewmodel

import com.dalingge.coinvista.core.common.base.viewmodel.BaseNetWorkViewModel
import com.dalingge.coinvista.core.data.repository.MarketRepository
import com.dalingge.coinvista.core.model.entity.SearchList
import kotlinx.coroutines.flow.Flow

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/8  11:00
 */
class SearchViewModel(
    private val marketRepository: MarketRepository,
) : BaseNetWorkViewModel<List<SearchList>>()  {


    init {
        super.executeRequest()
    }

    override fun requestApiFlow(): Flow<List<SearchList>> {
        return marketRepository.searchTrendingCoins()
    }


    /**
     * 执行搜索
     *
     * @param keyword 搜索关键词
     */
    fun onSearch(keyword: String) {

    }

}
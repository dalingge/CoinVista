package com.dalingge.coinvista.core.data.repository

import com.dalingge.coinvista.core.model.entity.NewsItem
import com.dalingge.coinvista.core.model.response.NetworkPageData
import com.dalingge.coinvista.core.network.datasource.news.NewsNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepository(private val newsNetworkDataSource: NewsNetworkDataSource) {


    /**
     *  按类型获取新闻
     *
     *  @param page 要检索的页码（从 1 开始索引）
     *
     */
    fun getNewsList(page: Int, ): Flow<List<NewsItem>> =
        flow {
            emit(newsNetworkDataSource.getNewsList(page, 20))
        }.flowOn(Dispatchers.IO)

    /**
     *  按类型获取新闻
     *
     *  @param type 想要获取的新闻类型 可选方案： handpicked， trending， latest， bullish， bearish
     *  @param page 要检索的页码（从 1 开始索引）
     *
     */
    fun getNewsType(type: String, page: Int, ): Flow<List<NewsItem>> =
        flow {
            emit(newsNetworkDataSource.getNewsType(type, page, 20))
        }.flowOn(Dispatchers.IO)


    /**
     *  新闻详情
     *
     *  @param id 要获取的新闻ID
     *
     */
    fun getNewsList(id: String): Flow<NewsItem> =
        flow {
            emit(newsNetworkDataSource.getNewsDetails(id))
        }.flowOn(Dispatchers.IO)
}
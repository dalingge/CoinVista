package com.dalingge.coinvista.core.network.datasource.news

import com.dalingge.coinvista.core.model.entity.NewsItem
import com.dalingge.coinvista.core.model.response.NetworkPageData


/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/10/21  18:16
 */
interface NewsNetworkDataSource {

    /**
     * 按类型获取新闻
     *
     *  @param page 要检索的页码（从 1 开始索引）
     *  @param limit 每页返回的数量
     */
    suspend fun getNewsList(page: Int, limit: Int, ): List<NewsItem>


    /**
     * 按类型获取新闻
     *
     *  @param type 想要获取的新闻类型 可选方案： handpicked， trending， latest， bullish， bearish
     *  @param page 要检索的页码（从 1 开始索引）
     *  @param limit 每页返回的数量
     */
    suspend fun getNewsType(type: String, page: Int, limit: Int, ): List<NewsItem>


    /**
     * 新闻详情
     *
     * @param id 要获取的新闻ID
     */
    suspend fun getNewsDetails(id: String, ): NewsItem

}
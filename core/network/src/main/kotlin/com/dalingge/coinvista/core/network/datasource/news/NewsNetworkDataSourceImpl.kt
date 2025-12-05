package com.dalingge.coinvista.core.network.datasource.news

import com.dalingge.coinvista.core.model.entity.NewsItem
import com.dalingge.coinvista.core.model.response.NetworkPageData
import com.dalingge.coinvista.core.network.base.BaseNetworkDataSource
import com.dalingge.coinvista.core.network.service.NewsService

/**
 *
 * @Description : 资讯相关数据源实现类
 * @Author :Dalingge
 * @Time :2025/10/21  17:54
 * @property newsService 资讯服务接口，用于发起实际的网络请求
 */
class NewsNetworkDataSourceImpl(private val newsService: NewsService): BaseNetworkDataSource(), NewsNetworkDataSource {

    /**
     * 按类型获取新闻
     *
     *  @param page 要检索的页码（从 1 开始索引）
     *  @param limit 每页返回的数量
     */
    override suspend fun getNewsList(page: Int, limit: Int, ): List<NewsItem> {
        return newsService.getNewsList(page,limit)
    }

    /**
     * 按类型获取新闻
     *
     *  @param type 想要获取的新闻类型 可选方案： handpicked， trending， latest， bullish， bearish
     *  @param page 要检索的页码（从 1 开始索引）
     *  @param limit 每页返回的数量
     */
    override suspend fun getNewsType(type: String, page: Int, limit: Int, ): List<NewsItem> {
        return newsService.getNewsType(type,page,limit)
    }

    /**
     * 新闻详情
     *
     * @param id 要获取的新闻ID
     */
    override suspend fun getNewsDetails(id: String): NewsItem {
        return newsService.getNewsDetails(id)
    }

}
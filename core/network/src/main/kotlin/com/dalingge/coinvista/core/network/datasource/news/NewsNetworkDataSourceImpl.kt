package com.dalingge.coinvista.core.network.datasource.news

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


}
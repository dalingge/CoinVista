package com.dalingge.coinvista.core.network.service

import com.dalingge.coinvista.core.model.entity.NewsItem
import com.dalingge.coinvista.core.model.response.NetworkPageData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 *
 * @Description :资讯相关接口
 * @Author :Dalingge
 * @Time :2025/10/21  10:30
 */
interface NewsService {


    /**
     * 按类型获取新闻
     *
     *  @param page 要检索的页码（从 1 开始索引）
     *  @param limit 每页返回的数量
     */
    @GET("/news")
    suspend fun getNewsList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): List<NewsItem>


    /**
     * 按类型获取新闻
     *
     *  @param type 想要获取的新闻类型 可选方案： handpicked， trending， latest， bullish， bearish
     *  @param page 要检索的页码（从 1 开始索引）
     *  @param limit 每页返回的数量
     */
    @GET("/news/type/{type}")
    suspend fun getNewsType(
        @Path("type") type: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): List<NewsItem>


    /**
     * 新闻详情
     *
     * @param id 要获取的新闻ID
     */
    @GET("/news/{id}")
    suspend fun getNewsDetails(
        @Path("id") id: String,
    ): NewsItem


}
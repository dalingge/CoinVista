package com.dalingge.coinvista.core.network.service

import com.dalingge.coinvista.core.model.entity.NFTsTrending
import com.dalingge.coinvista.core.model.response.NetworkPageData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @Description :NFT相关接口
 * @Author :丁博洋
 * @Time :2025/12/2  17:27
 */
interface NFTsService {


    /**
     * 获取热门nft
     * @param page 要检索的页码（从 1 开始索引）
     * @param limit 每页返回的数量
     */
    @GET("/nft/trending")
    suspend fun getNFTsTrending(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): NetworkPageData<NFTsTrending>
}
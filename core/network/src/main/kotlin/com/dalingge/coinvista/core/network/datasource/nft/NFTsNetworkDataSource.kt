package com.dalingge.coinvista.core.network.datasource.nft

import com.dalingge.coinvista.core.model.entity.NFTsTrending
import com.dalingge.coinvista.core.model.response.NetworkPageData

/**
 *
 * @Description :
 * @Author :Dalingge
 * @Time :2025/12/2  17:35
 */
interface NFTsNetworkDataSource {

    /**
     * 获取热门nft
     * @param page 要检索的页码（从 1 开始索引）
     * @param limit 每页返回的数量
     */
    suspend fun getNFTsTrending(page: Int,limit: Int): NetworkPageData<NFTsTrending>
}
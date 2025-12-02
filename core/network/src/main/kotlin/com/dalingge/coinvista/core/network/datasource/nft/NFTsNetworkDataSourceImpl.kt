package com.dalingge.coinvista.core.network.datasource.nft

import com.dalingge.coinvista.core.model.entity.NFTsTrending
import com.dalingge.coinvista.core.model.response.NetworkPageData
import com.dalingge.coinvista.core.network.base.BaseNetworkDataSource
import com.dalingge.coinvista.core.network.service.NFTsService

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/12/2  17:35
 */
class NFTsNetworkDataSourceImpl(private val nftsService: NFTsService): BaseNetworkDataSource(), NFTsNetworkDataSource {


    /**
     * 获取热门nft
     * @param page 要检索的页码（从 1 开始索引）
     * @param limit 每页返回的数量
     */
    override suspend fun getNFTsTrending(page: Int, limit: Int): NetworkPageData<NFTsTrending> {
        return nftsService.getNFTsTrending(page,limit)
    }
}
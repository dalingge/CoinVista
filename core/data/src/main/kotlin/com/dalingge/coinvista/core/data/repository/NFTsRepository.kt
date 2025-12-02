package com.dalingge.coinvista.core.data.repository

import com.dalingge.coinvista.core.model.entity.NFTsTrending
import com.dalingge.coinvista.core.model.response.NetworkPageData
import com.dalingge.coinvista.core.network.datasource.nft.NFTsNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/12/2  17:38
 */
class NFTsRepository(
    private val nftsNetworkDataSource: NFTsNetworkDataSource,
) {


    /**
     *  获取热门nft
     *
     *  @param page 要检索的页码（从 1 开始索引）
     */
    fun getNFTsTrending(page: Int, ): Flow<NetworkPageData<NFTsTrending>> =
        flow {
            emit(nftsNetworkDataSource.getNFTsTrending(page, 20))
        }.flowOn(Dispatchers.IO)
}
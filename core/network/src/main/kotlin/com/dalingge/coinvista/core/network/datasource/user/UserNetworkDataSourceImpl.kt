package com.dalingge.coinvista.core.network.datasource.user

import com.dalingge.coinvista.core.network.base.BaseNetworkDataSource
import com.dalingge.coinvista.core.network.service.UserService

/**
 *
 * @Description : 用户相关数据源实现类
 * @Author :丁博洋
 * @Time :2025/10/21  17:54
 * @property userService 用户服务接口，用于发起实际的网络请求
 */
class UserNetworkDataSourceImpl(private val userService: UserService): BaseNetworkDataSource(), UserNetworkDataSource {
}
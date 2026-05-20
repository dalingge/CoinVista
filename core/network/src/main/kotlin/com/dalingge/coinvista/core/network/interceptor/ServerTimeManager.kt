package com.dalingge.coinvista.core.network.interceptor

import android.os.SystemClock

/**
 * 服务器时间管理器
 * 保证在用户随意修改手机系统时间的情况下，依然能提供准确的服务器时间。
 */
object ServerTimeManager {

    // 服务器基准时间戳
    @Volatile
    private var baseServerTime: Long = 0L

    // 获取到基准时间时的设备开机时间
    @Volatile
    private var baseDeviceUptime: Long = 0L

    // 标记是否已经同步过
    @Volatile
    var isTimeSynced: Boolean = false
        private set

    /**
     * 更新服务器时间基准
     * @param serverTimeMillis 从网络端获取到的服务器当前时间戳
     */
    fun sync(serverTimeMillis: Long) {
        baseServerTime = serverTimeMillis
        baseDeviceUptime = SystemClock.elapsedRealtime()
        isTimeSynced = true
    }

    /**
     * 获取绝对准确的当前时间
     * @return 当前时间戳（毫秒）。如果尚未同步，则降级返回本地系统时间。
     */
    val currentTimeMillis: Long
        get() {
            return if (isTimeSynced) {
                // 公式：服务器当时的时间 + (当前开机时间 - 当时的开机时间)
                baseServerTime + (SystemClock.elapsedRealtime() - baseDeviceUptime)
            } else {
                // 兜底策略：还没请求过网络时，暂时使用本地时间
                System.currentTimeMillis()
            }
        }
}
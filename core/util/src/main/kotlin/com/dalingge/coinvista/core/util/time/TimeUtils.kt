package com.dalingge.coinvista.core.util.time

import java.util.TimeZone

/**
 *
 * @Description : 时间工具类
 * @Author :丁博洋
 * @Time :2025/10/31  10:54
 */
object TimeUtils {

    var utc: Int = 0
        get() {
            return TimeZone.getDefault().rawOffset / (1000 * 60 * 60)
        }
        private set


    fun utcFormat(): String {

        return if (utc.toString().startsWith("-")) {
            utc.toString()
        } else {
            "+${utc}"
        }
    }
}
package com.dalingge.coinvista.core.util.extension

import java.util.Locale

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/11/21  17:27
 */

// 定义一个扩展函数，支持 Long 和 Double
fun Number.toCompactMoney(): String {
    val value = this.toDouble()
    val trillion = 1_000_000_000_000.0
    val billion = 1_000_000_000.0
    val million = 1_000_000.0
    val thousand = 1_000.0

    val (formattedValue, suffix) = when {
        value >= trillion -> Pair(value / trillion, "T")
        value >= billion -> Pair(value / billion, "B")
        value >= million -> Pair(value / million, "M")
        value >= thousand -> Pair(value / thousand, "K")
        else -> Pair(value, "")
    }

    // 格式化为两位小数，例如 "3.01"
    return "$" + String.format(Locale.US, "%.2f%s", formattedValue, suffix)
}
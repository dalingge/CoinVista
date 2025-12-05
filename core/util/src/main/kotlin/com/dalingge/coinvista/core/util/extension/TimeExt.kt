package com.dalingge.coinvista.core.util.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/5  16:57
 */
/**
 * 将时间戳转换为友好的时间显示格式
 * 规则：
 * 1. 1分钟内 -> 刚刚
 * 2. 1小时内 -> X分钟前
 * 3. 今天内 -> X小时前
 * 4. 昨天 -> 昨天
 * 5. 更早 -> yyyy-MM-dd
 */
fun Long.toFriendlyTime(): String {
    val now = System.currentTimeMillis()
    val span = now - this

    // 防止手机时间不准导致出现了未来的时间，统一显示为“刚刚”
    if (span < 0) return "刚刚"

    val calendarNow = Calendar.getInstance()

    val calendarTarget = Calendar.getInstance()
    calendarTarget.timeInMillis = this

    // 格式化器
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // 1. 判断是否是今天
    if (calendarNow.get(Calendar.YEAR) == calendarTarget.get(Calendar.YEAR) &&
        calendarNow.get(Calendar.DAY_OF_YEAR) == calendarTarget.get(Calendar.DAY_OF_YEAR)) {

        val minutes = span / 60_000
        val hours = span / 3_600_000

        return when {
            minutes < 1 -> "刚刚"
            minutes < 60 -> "${minutes}分钟前"
            else -> "${hours}小时前"
        }
    }

    // 2. 判断是否是昨天
    // 将当前时间减去1天，看看是否和目标时间是同一天
    val calendarYesterday = Calendar.getInstance()
    calendarYesterday.add(Calendar.DAY_OF_YEAR, -1)

    if (calendarYesterday.get(Calendar.YEAR) == calendarTarget.get(Calendar.YEAR) &&
        calendarYesterday.get(Calendar.DAY_OF_YEAR) == calendarTarget.get(Calendar.DAY_OF_YEAR)) {
        return "昨天"
    }

    // 3. 其他情况（更早的时间）
    return dateFormat.format(Date(this))
}

// 如果你习惯用 Date 对象，也可以加一个 Date 的扩展
fun Date.toFriendlyTime(): String {
    return this.time.toFriendlyTime()
}
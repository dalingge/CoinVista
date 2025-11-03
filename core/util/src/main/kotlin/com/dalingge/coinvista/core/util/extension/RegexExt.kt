package com.dalingge.coinvista.core.util.extension

import java.util.regex.Pattern

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2025/10/31  12:20
 */
//只允许英文和数字
const val REGEX_ASCII_NUM = "^[A-Za-z0-9]+\$"

//正数、负数、和小数
const val REGEX_POSITIVE_NEGATIVE_INTEGER = "^(\\-|\\+)?\\d+(\\.\\d+)?\$"


fun String.hasAsciiNum(): Boolean {
    return isMatch(REGEX_ASCII_NUM)
}

fun String.hasPositiveNegativeInteger(): Boolean {
    return isMatch(REGEX_POSITIVE_NEGATIVE_INTEGER)
}

fun String.isMatch(regex: String): Boolean {
    return isNotEmpty() && Pattern.matches(regex, this)
}
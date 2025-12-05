package com.dalingge.coinvista.main.model

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2025/12/5  11:23
 */
enum class NewsState(val label: String,val type: String) {
    LATEST("最新","latest"),
    HANDPICKED("精选新闻","handpicked"),
    TRENDING("热门话题","trending"),
    BULLISH("看涨","bullish"),
    BEARISH("看跌","bearish"),
}
package com.dalingge.coinvista.core.model.request

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.util.Date

//{
//    "C": 1,
//    "S": "BTC",
//    "b": "68360.00",
//    "B": "0",
//    "a": "68360.00",
//    "A": "0",
//    "o": "69374.75",
//    "h": "69391.00",
//    "l": "67864.16",
//    "c": "68359.99",
//    "T": "1730636216421",
//    "V": "0"
//}
@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Ticker(

    @JsonNames(names = ["C"])
    val messageCode: Int = 0,
    @JsonNames(names = ["S"])
    val symbol: String = "",
    @JsonNames(names = ["b"])
    val bidPrice: String = "",
    @JsonNames(names = ["B"])
    val bidSize: String = "",
    @JsonNames(names = ["a"])
    val askPrice: String = "",
    @JsonNames(names = ["A"])
    val askSize: String = "",
    @JsonNames(names = ["o"])
    val openPrice: String = "",
    @JsonNames(names = ["h"])
    val highPrice: String = "",
    @JsonNames(names = ["l"])
    val lowPrice: String = "",
    @JsonNames(names = ["c"])
    val closePrice: String = "",
    @JsonNames(names = ["V"])
    val volume: String = "",
    @JsonNames(names = ["T"])
    val timeStamp: String = "",
)

//{
//    "C": 1,             // MessageCode 1:tick 5:candleLine
//    "S": "GC",          // Symbol
//    "I": "1m",          // Interval 说明参考 K线列表 Rest Api
//    "T": "1",          // noMDEntriesPriceType 说明参考 K线列表 Rest Api
//    "sT": "100",      // start Time
//    "eT": "200",         // endTime
//    "o": "6000",        // Open Price
//    "h": "6000",       // Hight Price
//    "l": "6000",        // Low Price
//    "c": "6000",       // Close Price
//    "v": "100",         // Volume
//    "pC": "100"       // preClose
//}
@Serializable
data class KLine(
    //@Json(name = "C")
    val messageCode: Int = 0,
    //@Json(name = "S")
    val symbol: String = "",
  //  @Json(name = "I")
    val interval: String = "",
    //@Json(name = "sT")
    val startTime: String = "",
   // @Json(name = "eT")
    val endTime: String = "",
   // @Json(name = "o")
    val openPrice: String = "",
  //  @Json(name = "h")
    val highPrice: String = "",
   // @Json(name = "l")
    val lowPrice: String = "",
   // @Json(name = "c")
    val closePrice: String = "",
   // @Json(name = "v")
    val volume: String = "",
) {
    fun time(): Date = Date(startTime.toLong())

//    fun getAreaData(isLine: Boolean): SeriesData {
//        return if (isLine) {
//            LineData(Time.Utc.fromDate(time()), closePrice.toFloat())
//        } else {
//            CandlestickData(
//                Time.Utc.fromDate(time()),
//                openPrice.toFloat(),
//                highPrice.toFloat(),
//                lowPrice.toFloat(),
//                closePrice.toFloat()
//            )
//        }
//    }
//
//    fun getKLineData(): KLineData {
//        return KLineData(startTime, endTime, openPrice, highPrice, lowPrice, closePrice, volume)
//    }
}

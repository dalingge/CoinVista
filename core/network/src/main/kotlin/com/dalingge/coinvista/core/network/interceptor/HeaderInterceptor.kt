package com.dalingge.coinvista.core.network.interceptor

import android.os.Build
import com.dalingge.coinvista.core.util.appVersionName
import com.dalingge.coinvista.core.util.deviceid.DeviceId
import com.dalingge.coinvista.core.util.time.TimeUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 *
 * @Description :
 * @Author :丁博洋
 * @Time :2024/10/9  15:25
 */
class HeaderInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        val url = chain.request().url
        val newUrl = url.newBuilder()
            .addQueryParameter("packtype", "4")
            .addQueryParameter("proxyid", "366")
            .addQueryParameter("deviceInfo", Build.MODEL)
            .addQueryParameter("utc", TimeUtils.utc.toString())

        //从request中获取原有的HttpUrl实例oldHttpUrl
        val path = chain.request().url.pathSegments.last()

        val isAddQueryParameter = path != "setApp"

        if (isAddQueryParameter){
            newUrl.addQueryParameter("version", appVersionName )
            newUrl.addQueryParameter("lang", "en")
            newUrl.addQueryParameter("userToken", "yitzh8ItpFfXTwqZcB-pucrL3-5qu4E1KwAVohOcQw8a_FCem2DcAlhuXx42D4-TlUjN-mDRljtf64h_egoTZy3MF8iIvyTbOsph-pEujrik7VtluOq0lA4ZYvTPgGiA")
        }

        val deviceId = DeviceId.getWidevineId()
        if (deviceId.isNotEmpty() && isAddQueryParameter) {
            newUrl.addQueryParameter("deviceId", deviceId)
        }

//        if (ClientData.adjustId.isNotEmpty()) {
//            newUrl.addQueryParameter("adid", ClientData.adjustId)
//        }
//
//        if (ClientData.googleId.isNotEmpty()) {
//            newUrl.addQueryParameter("gps_adid", ClientData.googleId)
//        }

        return chain.proceed(builder.url(newUrl.build()).build())
    }
}
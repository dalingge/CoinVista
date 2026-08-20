plugins {
    alias(libs.plugins.coinvista.android.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // 引入 model 模块
    implementation(projects.core.model)
    // 引入 util 模块
    implementation(projects.core.util)

    // 网络相关
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.serialization)
    // websocket相关
    implementation(libs.scarlet.core)
    implementation(libs.scarlet.okhttp)
    implementation(libs.scarlet.lifecycle)

    // 通过OkHttp的拦截器机制
    // 实现在应用通知栏显示网络请求功能
    // https://github.com/ChuckerTeam/chucker
    // debug 下的依赖
    debugImplementation(libs.chucker.library)
    // prod 下的空依赖
    releaseImplementation(libs.chucker.library.no)
}
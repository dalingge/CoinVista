// 配置项目级插件
plugins {
    // Android应用程序插件，用于构建Android应用
    alias(libs.plugins.android.application) apply false
    // Android库插件，用于构建Android库模块
    alias(libs.plugins.android.library) apply false
    // Kotlin Android插件，支持Kotlin语言开发
    alias(libs.plugins.kotlin.android) apply false
    // Kotlin Compose插件，用于Jetpack Compose UI开发
    alias(libs.plugins.kotlin.compose) apply false
    // KSP (Kotlin Symbol Processing)插件，用于注解处理
    alias(libs.plugins.ksp) apply false
}
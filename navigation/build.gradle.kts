plugins {
    alias(libs.plugins.coinvista.android.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // 导航
    api(libs.androidx.navigation.compose)
    // Kotlin 序列化
    implementation(libs.kotlinx.serialization.json)
}


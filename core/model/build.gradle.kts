plugins {
    alias(libs.plugins.coinvista.android.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // kotlin序列化
    implementation(libs.kotlinx.serialization.json)
}
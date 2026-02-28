plugins {
    alias(libs.plugins.coinvista.android.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // 导航
    implementation(libs.androidx.navigation3.runtime)
    // Kotlin 序列化
    implementation(libs.kotlinx.serialization.json)

    // 全局应用状态（登录态拦截）
    implementation(projects.core.data)

    // 数据模型（用于 NavigationResultKey 绑定具体实体，例如 Address）
    implementation(projects.core.model)
}


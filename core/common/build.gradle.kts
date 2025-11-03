plugins {
    alias(libs.plugins.coinvista.android.library.compose)
}

dependencies {
    // 引入 navigation 模块
    implementation(projects.navigation)
    // 引入 data 模块
    implementation(projects.core.data)
    // 引入 model 模块
    implementation(projects.core.model)
    // 引入工具模块
    api(projects.core.util)
}
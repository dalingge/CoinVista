plugins {
    alias(libs.plugins.coinvista.android.library.compose)
}

dependencies {

    implementation(libs.androidx.navigation3.runtime)

    // 引入 navigation 模块
    implementation(projects.core.navigation)
    // 引入 data 模块
    implementation(projects.core.data)
    // 引入 model 模块
    implementation(projects.core.model)
}
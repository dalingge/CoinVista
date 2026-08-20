plugins {
    alias(libs.plugins.coinvista.android.library)
}

dependencies {

    // 引入 model 模块
    implementation(projects.core.model)
}
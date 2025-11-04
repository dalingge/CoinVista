plugins {
    alias(libs.plugins.coinvista.android.library.compose)
}

dependencies {
    implementation(projects.core.design)
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(projects.core.util)
    // 图片加载框架
    implementation(libs.coil.compose)
    // lottie 动画
    implementation(libs.lottie.compose)
}
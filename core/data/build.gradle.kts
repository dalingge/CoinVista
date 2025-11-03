plugins {
    alias(libs.plugins.coinvista.android.library.compose)
}

dependencies {
    // 引入 model 模块
    implementation(projects.core.model)
    // 引入网络模块
    implementation(projects.core.network)
    // 引入数据存储模块
    implementation(projects.core.datastore)
    // 引入数据库模块
    implementation(projects.core.database)
}
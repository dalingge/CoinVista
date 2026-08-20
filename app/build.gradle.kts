plugins {
    alias(libs.plugins.coinvista.android.application.compose)

}

android {

    signingConfigs {
        create("config") {
//            storeFile = file("$rootDir"+SigningConfigs.KEY_SIGNING_FILE)
//            storePassword = SigningConfigs.KEYSTORE_PASSWORD
//            keyAlias = SigningConfigs.KEY_ALIAS
//            keyPassword = SigningConfigs.KEY_PASSWORD

            // 启用所有签名方案以确保最大兼容性
            enableV1Signing = true  // JAR 签名 (Android 1.0+)
            enableV2Signing = true  // APK 签名 v2 (Android 7.0+)
            enableV3Signing = true  // APK 签名 v3 (Android 9.0+)
            enableV4Signing = true  // APK 签名 v4 (Android 11.0+)
        }
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
         //   signingConfig = signingConfigs.getByName("config")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            isMinifyEnabled = true   // 是否启用代码压缩
            isShrinkResources = true  // 资源压缩
       //    signingConfig = signingConfigs.getByName("config")
            // 配置ProGuard规则文件
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    // 首页模块
    implementation(projects.feature.main)
    //市场交易模块
    implementation(projects.feature.market)
    // 登录(认证)模块
    implementation(projects.feature.auth)
    // 通用模块
    implementation(projects.feature.common)

    implementation(projects.core.design)
    implementation(projects.core.data)
    implementation(projects.core.common)
    implementation(projects.core.network)

    //图片相关
    implementation(libs.coil.core)
    implementation(libs.coil.okhttp)

    implementation(libs.nav3.router.runtime)

    implementation(libs.androidx.lifecycle.navigation3)

    // 测试依赖
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

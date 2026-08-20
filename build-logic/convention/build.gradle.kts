import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// 应用Kotlin DSL插件，使构建脚本能够使用Kotlin语言编写
plugins {
    `kotlin-dsl`
}

group = "com.dalingge.coinvista.plugin.buildlogic"

// 配置构建逻辑插件以目标 JDK 17
// 这与用于构建项目的 JDK 匹配，与设备上运行的内容无关
java {
    // 设置Java源代码和目标字节码的兼容性版本为Java 17
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

// 声明构建逻辑模块的依赖
dependencies {
    // 添加Android Gradle插件依赖（仅编译时需要）
    compileOnly(libs.android.build.logic)
    // 添加Kotlin Gradle插件依赖（仅编译时需要）
    compileOnly(libs.kotlin.build.logic)
    // 添加KSP注解处理器插件依赖（仅编译时需要）
    compileOnly(libs.ksp.build.logic)
}

gradlePlugin {
    plugins {
        // 注册Android应用程序插件
        register("androidApplication") {
            id = "com.dalingge.coinvista.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        // 注册Android应用程序Compose插件
        register("androidApplicationCompose") {
            id = "com.dalingge.coinvista.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        // 注册Android库插件
        register("androidLibrary") {
            id = "com.dalingge.coinvista.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        // 注册Android库Compose插件
        register("androidLibraryCompose") {
            id = "com.dalingge.coinvista.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        // 注册Android Feature模块插件
        register("androidFeature") {
            id = "com.dalingge.coinvista.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        // 注册Koin依赖注入插件
        register("androidKoin") {
            id = "com.dalingge.coinvista.android.koin"
            implementationClass = "KoinConventionPlugin"
        }
        // 注册Android Firebase模块插件
//        register("androidFirebase") {
//            id = "com.dalingge.coinvista.android.firebase"
//            implementationClass = "AndroidFirebaseConventionPlugin"
//        }
    }
}

// 配置任务以处理重复资源
tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
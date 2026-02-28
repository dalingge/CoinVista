// 启用类型安全的项目访问器功能
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Gradle插件管理配置
pluginManagement {
    // 包含build-logic目录作为构建逻辑模块
    includeBuild("build-logic")
    // 配置插件仓库
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

    }
}

// 依赖解析管理配置
dependencyResolutionManagement {
    // 设置仓库模式为严格模式，禁止在项目中单独配置仓库
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    // 配置项目级依赖仓库
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}


rootProject.name = "CoinVista"

include(":app")

// 核心模块
include(":core:analytics")
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:design")
include(":core:model")
include(":core:network")
include(":core:ui")
include(":core:util")
include(":core:navigation")
// feature 功能模块
include(":feature:main")
include(":feature:auth")
include(":feature:common")
include(":feature:market")


// JDK 版本检查：确保使用 JDK 17 或更高版本进行构建
check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    StellaTrade 项目需要 JDK 17+ 但当前使用的是 JDK ${JavaVersion.current()}。
    Java Home: [${System.getProperty("java.home")}]
    请参考: https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}

 
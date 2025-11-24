import com.android.build.gradle.LibraryExtension
import com.dalingge.coinvista.plugin.configureKotlinAndroid
import com.dalingge.coinvista.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.io.FileInputStream
import java.util.Properties

/**
 * Android库模块构建插件
 *
 * 该插件用于配置Android库模块的基本构建设置，主要功能包括：
 * - 自动生成模块的命名空间
 * - 配置SDK版本和Java编译选项
 * - 管理通用依赖
 * - 配置ProGuard规则
 *
 * 插件会根据模块在项目中的位置自动生成合适的命名空间，支持以下模块类型：
 * - feature模块：com.stella.app.feature.xxx
 * - core模块：com.stella.app.core.xxx
 * - navigation模块：com.stella.app.navigation
 * - 其他模块：根据模块路径生成
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        with(target) {

            //  读取 local.properties 文件
            val localProperties = Properties()
            val localPropertiesFile = rootProject.file("local.properties")
            if (localPropertiesFile.exists()) {
                localProperties.load(FileInputStream(localPropertiesFile))
            }

            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {

                // 获取项目目录路径
                val projectDir = project.projectDir.path
                // 匹配feature模块路径
                val featureMatch = Regex(".*[\\\\/](feature[\\\\/][^\\\\/]+).*").find(projectDir)
                // 匹配core模块路径
                val coreMatch = Regex(".*[\\\\/](core[\\\\/][^\\\\/]+).*").find(projectDir)
                // 根据模块类型生成命名空间
                namespace = when {
                    // feature模块命名空间
                    featureMatch != null -> {
                        val featurePath = featureMatch.groupValues[1].replace("[\\\\/]".toRegex(), ".")
                        "${libs.findVersion("packageName").get()}.$featurePath"
                    }
                    // core模块命名空间
                    coreMatch != null -> {
                        val corePath = coreMatch.groupValues[1].replace("[\\\\/]".toRegex(), ".")
                        "${libs.findVersion("packageName").get()}.$corePath"
                    }
                    // navigation模块命名空间
                    project.path == ":navigation" -> {
                        "${libs.findVersion("packageName").get()}.navigation"
                    }
                    // 其他模块命名空间
                    else -> {
//                        val modulePath = project.path.removePrefix(":").replace(":", ".")
//                        "${libs.findVersion("packageName").get()}.$modulePath"
                        libs.findVersion("packageName").get().toString() + name
                    }
                }

                println("配置模块: ${project.path} 的命名空间为: $namespace")

                configureKotlinAndroid(this)

                defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()


                flavorDimensions += listOf("env")
                productFlavors {
                    create("dev") {
                        dimension = "env"
                        buildConfigField("Boolean", "DEBUG", "true")
                        buildConfigField("String", "API_KEY", "\"${localProperties["COINSTATS_API_KEY"]}\"")
                    }
                    create("prod") {
                        dimension = "env"
                        buildConfigField("Boolean", "DEBUG", "false")
                        buildConfigField("String", "API_KEY", "\"${localProperties["COINSTATS_API_KEY"]}\"")
                    }
                }
            }


            configureDependencies()
        }
    }
}


/**
 * 配置库模块的通用依赖
 *
 * 添加Android开发所需的基础依赖，包括：
 * - AndroidX Core KTX
 * - AppCompat
 * - Material Design
 * - 测试相关依赖
 */
internal fun Project.configureDependencies() {
    dependencies {
        "implementation"(libs.findLibrary("androidx.core.ktx").get())
        "implementation"(libs.findLibrary("androidx.appcompat").get())
        "implementation"(libs.findLibrary("material").get())
        "testImplementation"(libs.findLibrary("junit").get())
        "androidTestImplementation"(libs.findLibrary("androidx.junit").get())
        "androidTestImplementation"(libs.findLibrary("androidx.espresso.core").get())
    }
}

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.dalingge.coinvista.plugin.configureKotlinAndroid
import com.dalingge.coinvista.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Android应用级构建插件
 *
 * 该插件用于配置Android应用模块的基本构建设置，包括：
 * - 应用ID和版本信息
 * - SDK版本配置
 * - Java编译选项
 * - 产品变体配置
 *
 * 主要通过扩展Android Gradle Plugin的ApplicationExtension来实现配置
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                // 使用统一的 Kotlin Android 配置
                configureKotlinAndroid(this)

                namespace = libs.findVersion("packageName").get().toString()
                // 默认配置
                defaultConfig {
                    applicationId = libs.findVersion("applicationId").get().toString()
                    versionCode = libs.findVersion("versionCode").get().toString().toInt()
                    versionName = libs.findVersion("versionName").get().toString()
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()

                    ndk {
                        abiFilters.addAll(listOf("armeabi-v7a","arm64-v8a"))
                    }
                }

             //   configureFlavors(this)

                flavorDimensions += listOf("env")
                productFlavors {
                    create("dev") {
                        dimension = "env"
                    }
                    create("prod") {
                        dimension = "env"
                    }
                }

            }

            extensions.configure<BaseAppModuleExtension> {
                applicationVariants.all {
                    outputs.all {
                        if (this is ApkVariantOutputImpl) {
                            val releaseTime: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())
                            this.outputFileName = "${rootProject.name}_${versionName}_${flavorName}_${buildType.name}_${releaseTime}.apk"
                        }
                    }
                }
            }
        }
    }
}
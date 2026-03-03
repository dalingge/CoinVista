import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.internal.api.ApkVariantOutputImpl
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
                apply("com.dalingge.coinvista.android.hilt")
            //    apply("org.jetbrains.kotlin.android")
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

            extensions.configure<ApplicationAndroidComponentsExtension> {
                onVariants(selector().all()) { variant ->
                    variant.outputs.forEach { output ->
                        if (output is ApkVariantOutputImpl) {
                            // 4. 获取构建信息
                            // 注意：新 API 中获取这些信息的方式略有不同
                            val project = target // 或者你在 Plugin 中持有的 project 对象
                            val appName = project.rootProject.name

                            // 获取 versionName (这是一个 Provider，可能为空)
                            // 如果你在 defaultConfig 里没写，这里可能拿不到，需注意空安全
                            val verName = output.versionName.getOrElse("1.0")

                            // 获取 flavor 和 buildType
                            // flavorName 如果没有风味可能是 null 或空字符串
                            val flavorPart = variant.flavorName ?: ""
                            val buildTypePart = variant.buildType ?: "debug"

                            // 生成时间 (建议放在这里，每次配置时生成)
                            val releaseTime = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())

                            // 5. 拼接文件名
                            // 逻辑：如果有 flavor，加下划线，否则不加
                            val flavorSegment = if (flavorPart.isNotEmpty()) "${flavorPart}_" else ""

                            // 6. 设置输出文件名
                            output.outputFileName = "${appName}_${verName}_${flavorSegment}${buildTypePart}_${releaseTime}.apk"
                        }
                    }
                }
            }
        }
    }
}
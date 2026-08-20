import com.dalingge.coinvista.plugin.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

/**
 *
 * @Description :
 * @Author : Dalingge
 * @Time :2026/3/3  14:29
 */
class KoinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        with(target) {

            // 支持 Android 模块，基于 AndroidBasePlugin
            pluginManager.withPlugin("com.android.base") {

                apply(plugin = "io.insert-koin.compiler.plugin")

                dependencies {
                    // Koin注入工具
                    "implementation"(platform( libs.findLibrary("koin.bom").get()))
                    "implementation"(libs.findLibrary("koin.annotations").get())
                    "implementation"(libs.findLibrary("koin.android").get())
                    "implementation"(libs.findLibrary("koin.compose").get())
                }
            }
        }
    }
}
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Android Feature模块构建插件
 *
 * 该插件用于配置Android功能模块的构建设置，主要功能包括：
 * - 应用基础的Android库和Compose配置
 * - 启用BuildConfig生成
 * - 配置Feature模块通用依赖
 *
 * Feature模块是应用的功能模块，通常包含特定功能的UI和业务逻辑
 */
class AndroidFeatureConventionPlugin : Plugin<Project>{
    /**
     * 插件应用入口
     *
     * @param target 目标项目实例
     */
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.dalingge.coinvista.android.library.compose") // 应用Android库和Compose配置
            }

            // 配置Feature模块依赖
            dependencies {

                // 项目内基础模块依赖
                "implementation"(project(":navigation")) // 导航模块
                "implementation"(project(":core:design")) // 设计系统
                "implementation"(project(":core:data")) // 数据
                "implementation"(project(":core:common")) // 公共
                "implementation"(project(":core:model")) // 模型
                "implementation"(project(":core:ui")) // 模型

                // Jetpack Navigation Compose导航框架
               // "implementation"(libs.findLibrary("androidx.navigation.compose").get())
            }
        }
    }
}
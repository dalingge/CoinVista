/*
 * Copyright 2023 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.dalingge.coinvista.plugin

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * Project类的扩展属性，用于简化版本目录的访问
 *
 * 该扩展属性为Project类添加了一个名为libs的只读属性，用于访问项目的版本目录（Version Catalog）。
 * 版本目录是Gradle 7.0引入的新特性，用于集中管理项目依赖的版本信息。
 *
 * 使用方式：
 * ```kotlin
 * // 获取依赖版本
 * val version = libs.findVersion("someVersion").get()
 * // 获取依赖库
 * val library = libs.findLibrary("someLibrary").get()
 * ```
 *
 * @return VersionCatalog 返回名为"libs"的版本目录实例
 */
val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

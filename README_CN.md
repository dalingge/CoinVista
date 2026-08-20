**简体中文** | [English](README.md)

<div align="center">

<img src="docs/image/ic_logo.png" alt="CoinVista logo" width="120" />

# CoinVista

**Vision powered by data.（数据驱动视野）**

一个基于 Kotlin、Jetpack Compose 和整洁多模块架构构建的现代加密货币行情 Android 应用。

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.0-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-blueviolet?logo=jetpackcompose)](https://developer.android.com/develop/ui/compose)
[![minSdk](https://img.shields.io/badge/minSdk-26-green?logo=android)](https://developer.android.com)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

</div>

## 项目简介

CoinVista 通过快速、响应式、Material 3 风格的界面，提供实时加密货币价格、币圈资讯与投资组合跟踪。项目采用多模块架构，配合共享的 Gradle 约定插件与自定义注解式导航路由框架。

## 功能特性

- 📈 **行情（Markets）** — 实时价格、市值与图表
- 📰 **资讯（News）** — 精选加密货币新闻流
- 💼 **投资组合（Portfolio）** — 追踪持仓与仓位
- 👤 **我的（Mine）** — 个人中心
- 🔍 **搜索（Search）** — 全市场搜索
- 🌐 **内置浏览器** — 在应用内 WebView 中打开关联内容

## 技术栈

| 分类     | 技术                                                                       |
| -------- | -------------------------------------------------------------------------- |
| 语言     | Kotlin 2.4.0                                                               |
| UI       | Jetpack Compose + Material 3                                                |
| 架构     | MVVM、`StateFlow`、Repository 模式                                          |
| 依赖注入 | [Koin](https://insert-koin.io) 4.2 + Koin 编译插件                          |
| 导航     | 自定义注解式路由框架（`@Screen` + KSP 代码生成）                              |
| 网络     | Retrofit 3、OkHttp 5、kotlinx.serialization                                 |
| 实时推送 | [Scarlet](https://github.com/Tinder/Scarlet)（WebSocket）                    |
| 图片加载 | [Coil](https://coil-kt.github.io/coil) 3                                     |
| 存储     | MMKV、Room、DataStore                                                       |
| 构建     | Gradle Kotlin DSL + 约定插件（`build-logic`）                                |

## 架构

项目采用分层、多模块的结构：

```
CoinVista/
├── app/            # 应用入口：Application、MainActivity、Koin 与导航初始化
├── build-logic/    # 约定插件（共享的 Gradle 配置）
├── core/           # 可复用的基础设施（不含业务逻辑）
│   ├── analytics/  #   埋点跟踪
│   ├── common/     #   ViewModel 基类、Result 封装
│   ├── data/       #   Repository 与全局 AppState
│   ├── database/   #   Room 数据库
│   ├── datastore/  #   DataStore 偏好存储
│   ├── design/     #   设计系统（主题、颜色、字体）
│   ├── model/      #   数据模型（request/response）
│   ├── network/    #   Retrofit / OkHttp / Scarlet WebSocket
│   ├── ui/         #   可复用 Compose 组件
│   └── util/       #   工具函数
└── feature/        # 功能模块（UI + ViewModel）
    ├── main/       #   主界面（底部导航：行情/资讯/投资组合/我的）
    ├── market/     #   行情与搜索
    ├── auth/       #   认证（空壳，待实现）
    └── common/     #   共享功能（WebView 等）
```

### 关键设计决策

- **导航** 采用自定义注解式路由框架：在 `@Composable` 上标注 `@Screen(route = "...")`，KSP 处理器据此生成路由目标类与各模块的 `init*()` 初始化函数，统一由 `NavCenter` 单例驱动。
- **依赖注入** 按模块集中在各自 `dl/` 包下（`networkModule`、`mainModule`、`marketModule` 等），并在 `Application.onCreate()` 中一次性 `startKoin`。
- **网络层** 遵循仓库模式：`*NetworkDataSource` 接口 + `*NetworkDataSourceImpl` 实现，通过 Koin `single<Impl>() bind Interface::class` 注入。
- **状态管理** 通过 `StateFlow` 暴露 UI 状态，配合共享基类（`BaseViewModel`、`BaseNetWorkViewModel`、`BaseNetWorkListViewModel`）与统一的 `ResultHandler` + `asResult()` 流水线。

## 快速开始

### 环境要求

- **JDK 17**
- **Android SDK 36**（compile/target），最低支持 26
- 一个 **CoinStats API Key**（[coinstats.app](https://coinstats.app)）

### 配置

在 `local.properties` 中添加你的 API Key：

```properties
COINSTATS_API_KEY=your_api_key_here
```

> **注意：** 导航路由框架（`com.yiqun.nav`）托管在私有 GitLab Maven 仓库（配置见 `settings.gradle.kts`）。构建本项目需要具备该仓库的访问权限。

### 构建

```bash
# 构建调试版 APK
./gradlew assembleDebug

# 安装到已连接的设备
./gradlew installDebug

# 仅构建 app 模块
./gradlew :app:assembleDebug
```

### 测试

```bash
# 运行所有单元测试
./gradlew test

# 运行单个模块的测试
./gradlew :core:common:test

# 在已连接设备上运行仪器化测试
./gradlew connectedDebugAndroidTest
```

### Lint

```bash
./gradlew lint
```

## 开源协议

本项目采用 [MIT 协议](LICENSE)。

Copyright (c) 2025 达令哥

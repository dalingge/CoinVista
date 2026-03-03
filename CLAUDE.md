# CLAUDE.md

本文件为 Claude Code (claude.ai/code) 在此代码库中工作时提供指导。

## 项目概述

CoinVista 是一个使用 Kotlin、Jetpack Compose 和现代 Android 架构构建的多模块 Android 应用程序。项目使用 Gradle Kotlin DSL 和约定插件，确保各模块配置一致。

## 开发命令

### 构建与运行
- `./gradlew build` – 构建所有变体
- `./gradlew assembleDebug` – 构建调试版 APK
- `./gradlew installDebug` – 在已连接的设备上安装调试版 APK
- `./gradlew :app:assembleDebug` – 仅构建 app 模块的调试变体

### 测试
- `./gradlew test` – 运行所有单元测试
- `./gradlew connectedDebugAndroidTest` – 在已连接的设备上运行仪器化测试
- `./gradlew :module:test` – 运行特定模块的测试（例如 `:core:common:test`）

### 代码质量
- `./gradlew lint` – 运行 Android lint 检查
- `./gradlew ktlintCheck` – 检查 Kotlin 代码风格（如果配置了 ktlint）
- `./gradlew detekt` – 静态代码分析（如果配置了 detekt）

### 依赖管理
- `./gradlew dependencies` – 列出所有模块的依赖关系
- `./gradlew :app:dependencies` – 列出特定模块的依赖关系

### 项目结构
项目采用模块化架构，职责分离清晰：

```
CoinVista/
├── app/ – 主应用模块
├── build-logic/ – 构建配置的约定插件
├── core/ – 共享基础设施模块
│   ├── analytics/ – 分析跟踪
│   ├── common/ – 通用工具和共享依赖
│   ├── data/ – 应用状态管理 (AppState)
│   ├── database/ – 本地数据库 (Room)
│   ├── datastore/ – DataStore 偏好设置
│   ├── design/ – 设计系统、主题、UI 组件
│   ├── model/ – 数据模型
│   ├── network/ – 网络层 (Retrofit, OkHttp, WebSocket)
│   ├── ui/ – 可重用 UI 组件
│   ├── util/ – 工具函数
│   └── navigation/ – 导航基础设施 (AppNavigator, NavigationService)
└── feature/ – 功能模块
    ├── main/ – 主功能模块
    ├── auth/ – 认证功能模块
    ├── market/ – 市场/交易功能模块
    └── common/ – 共享功能组件
```

## 架构

### 导航
- 使用 **Navigation 3** (`androidx.navigation3`) 和自定义 `AppNavigator` 实现集中式导航控制
- `AppNavHost` 在 app 模块中协调跨功能图的导航
- 导航是类型安全的，路由对象定义在 `core.navigation.routes.*` 中
- 功能模块定义自己的导航图（例如 `marketGraph()`）
- ViewModel 通过 Koin 注入的 `AppNavigator` 进行导航

### 依赖注入
- **Koin** 在整个应用中用于依赖注入
- 模块按层定义（例如 `networkModule`、`appModule`）
- `AppState` 作为单例提供，用于全局状态管理

### 网络层
- 使用 **Retrofit** 和 Kotlinx Serialization 处理 REST API
- **OkHttp** 包含用于日志记录（调试时使用 Chucker）和请求头的拦截器
- 通过自定义拦截器支持 **多基址 URL**
- 使用 **Coil** 加载图片，配置了 OkHttp 客户端
- 网络数据源遵循仓库模式，使用 `*NetworkDataSource` 接口

### 状态管理
- `AppState` 类管理全局应用状态（登录状态、用户信息等）
- 使用 Kotlin 协程的 `StateFlow` 实现响应式状态更新
- ViewModel 将 UI 状态暴露为 `StateFlow` 或 `SharedFlow`

### UI 层
- 使用 **Jetpack Compose** 和 Material 3 设计系统构建 UI
- 使用 **SharedTransitionLayout** 实现协调的导航动画
- 主题和设计令牌定义在 `core.design` 中
- 可重用的 UI 组件位于 `core.ui`

### 构建系统
- `build-logic/` 中的 **Gradle 约定插件** 强制执行一致的配置
- 插件 ID：
  - `com.dalingge.coinvista.android.application` – 应用模块
  - `com.dalingge.coinvista.android.application.compose` – 带 Compose 的应用模块
  - `com.dalingge.coinvista.android.library` – 库模块
  - `com.dalingge.coinvista.android.library.compose` – 带 Compose 的库模块
  - `com.dalingge.coinvista.android.feature` – 功能模块
- 依赖集中在 `gradle/libs.versions.toml` 中管理

## 模块依赖关系

- **App 模块** 依赖于所有功能模块和核心模块
- **功能模块** 依赖于核心模块（导航、数据、设计等），但不依赖于其他功能模块
- **核心模块** 之间的依赖关系有限；`core.common` 通常聚合通用依赖

## 重要模式

### ViewModel 中的导航
```kotlin
class MyViewModel(private val navigator: AppNavigator) : ViewModel() {
    fun onItemClick() {
        navigator.navigateTo(MarketRoutes.Detail(id = "123"))
    }
}
```

### 观察导航结果
```kotlin
viewModelScope.launch {
    navigator.resultEvents(RefreshResultKey).collect { result ->
        // Handle result
    }
}
```

### 网络数据源
网络数据源实现接口模式：
```kotlin
interface MarketNetworkDataSource {
    suspend fun getMarketData(): Result<MarketData>
}

class MarketNetworkDataSourceImpl(
    private val marketService: MarketService
) : MarketNetworkDataSource {
    override suspend fun getMarketData(): Result<MarketData> = runCatching {
        marketService.getMarketData()
    }
}
```

## 配置

- **最小 SDK**: 26 (Android 8.0)
- **编译 SDK**: 36
- **目标 SDK**: 36
- **Kotlin**: 2.3.10
- **JDK**: 17 (构建必需)
- **Compose BOM**: 2026.02.00

## 注意事项

- 项目在某些文件中使用中文注释；编辑时请保持一致性。
- Navigation 3 是实验性的；更新依赖时请检查 API 变更。
- WebSocket 支持已配置，但目前在 `NetworkModule.kt` 中被注释。
- 部分 `AppState` 功能是存根实现；需要时请实现。
- 创建新模块时始终使用约定插件，以确保配置一致性。
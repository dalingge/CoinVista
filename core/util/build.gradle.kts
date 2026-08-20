plugins {
    alias(libs.plugins.coinvista.android.library)
}

dependencies {
    // 腾讯存储 https://github.com/Tencent/MMKV
    implementation(libs.mmkv)

    // 权限框架：https://github.com/getActivity/XXPermissions
    implementation(libs.xxpermissions)
}
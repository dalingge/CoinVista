package com.dalingge.coinvista.core.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import androidx.core.content.pm.PackageInfoCompat

lateinit var application: Application
  internal set

inline val packageName: String get() = application.packageName

inline val packageInfo: PackageInfo
  get() = application.packageManager.getPackageInfo(packageName, 0)

inline val appName: String
  get() = application.applicationInfo.loadLabel(application.packageManager).toString()

inline val appIcon: Drawable get() = packageInfo.applicationInfo!!.loadIcon(application.packageManager)

inline val appVersionName: String get() = packageInfo.versionName?:""

inline val appVersionCode: Long get() = PackageInfoCompat.getLongVersionCode(packageInfo)

inline val isAppDebug: Boolean get() = application.isAppDebug

inline val Application.isAppDebug: Boolean
  get() = packageManager.getApplicationInfo(packageName, 0).flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

inline val isAppDarkMode: Boolean
  get() = (application.resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES

fun launchAppSettings(): Boolean =
  Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    .apply { data = Uri.fromParts("package", packageName, null) }
    .startForActivity()

fun relaunchApp(killProcess: Boolean = true) =
  application.packageManager.getLaunchIntentForPackage(packageName)?.let {
    it.addFlags(FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_CLEAR_TOP)
      startActivity(it)
    if (killProcess) Process.killProcess(Process.myPid())
  }

fun doOnAppStatusChanged(onForeground: ((Activity) -> Unit)? = null, onBackground: ((Activity) -> Unit)? = null) =
  doOnAppStatusChanged(object : OnAppStatusChangedListener {
    override fun onForeground(activity: Activity) {
      onForeground?.invoke(activity)
    }

    override fun onBackground(activity: Activity) {
      onBackground?.invoke(activity)
    }
  })

fun doOnAppStatusChanged(listener: OnAppStatusChangedListener) {
  AppInitializer.Companion.onAppStatusChangedListener = listener
}

interface OnAppStatusChangedListener {
  fun onForeground(activity: Activity)
  fun onBackground(activity: Activity)
}

fun Application.doOnActivityLifecycle(
  onActivityCreated: ((Activity, Bundle?) -> Unit)? = null,
  onActivityStarted: ((Activity) -> Unit)? = null,
  onActivityResumed: ((Activity) -> Unit)? = null,
  onActivityPaused: ((Activity) -> Unit)? = null,
  onActivityStopped: ((Activity) -> Unit)? = null,
  onActivitySaveInstanceState: ((Activity, Bundle?) -> Unit)? = null,
  onActivityDestroyed: ((Activity) -> Unit)? = null,
): Application.ActivityLifecycleCallbacks =
  object : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
      onActivityCreated?.invoke(activity, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
      onActivityStarted?.invoke(activity)
    }

    override fun onActivityResumed(activity: Activity) {
      onActivityResumed?.invoke(activity)
    }

    override fun onActivityPaused(activity: Activity) {
      onActivityPaused?.invoke(activity)
    }

    override fun onActivityStopped(activity: Activity) {
      onActivityStopped?.invoke(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
      onActivitySaveInstanceState?.invoke(activity, outState)
    }

    override fun onActivityDestroyed(activity: Activity) {
      onActivityDestroyed?.invoke(activity)
    }
  }.also {
    registerActivityLifecycleCallbacks(it)
  }

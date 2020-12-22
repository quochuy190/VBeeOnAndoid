package vn.neo.smsvietlott.common.utils

import android.content.Context
import android.content.pm.PackageManager
import com.vn.vbeeon.R
import com.vn.vbeeon.VBeeOnApplication
import io.reactivex.Scheduler

object AppUtils {

    private lateinit var context: Context

    fun setup(context: Context) {
        AppUtils.context = context
    }

    fun packageName(): String {
        return context.packageName
    }

    fun versionCode(): Int {
        val mgr = context.packageManager
        val info = mgr.getPackageInfo(context.packageName, PackageManager.GET_META_DATA)
        return info.versionCode
    }

    fun versionName(): String {
        val mgr = context.packageManager
        val info = mgr.getPackageInfo(context.packageName, PackageManager.GET_META_DATA)
        return info.versionName
    }

    fun apiBaseUrl(): String {
        return getString(R.string.api_base_url)
    }

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getInteger(resId: Int): Int {
        return context.resources.getInteger(resId)
    }

    fun myApp(): VBeeOnApplication {
        return context.applicationContext as VBeeOnApplication
    }
}
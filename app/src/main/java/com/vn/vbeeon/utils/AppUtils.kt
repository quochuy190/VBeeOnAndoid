package com.vn.vbeeon.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.os.Build
import com.vn.vbeeon.R
import com.vn.vbeeon.VBeeOnApplication
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object AppUtils {
    fun AssetManager.readFile(fileName: String) = open(fileName)
        .bufferedReader()
        .use { it.readText() }

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

    fun versionName(contextApp: Context): String {
        val mgr = contextApp.packageManager
        val info = mgr.getPackageInfo(contextApp.packageName, PackageManager.GET_META_DATA)
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

    fun currentTime():String{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            var answer: String =  current.format(formatter)
            return answer
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("HH:mm")
            val answer: String = formatter.format(date)
            return answer
        }
    }
    fun getSecone():Int{
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        val second = c.get(Calendar.SECOND)
        return second
    }

    fun checkCurrentDateTime(valid_until: String):Boolean{
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val strDate = sdf.parse(valid_until)
        if (Date().after(strDate)) {
            return true
        }
        return false
    }
    fun getAgefromBirthday( dateString : String):Int{
//        val dateFormat = "dd/MM/yyyy"
//        val dtf = DateTimeFormatter.ofPattern(dateFormat)
        try {
            val format =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val d = format.parse(dateString)
            val year = d.year
            val c = Calendar.getInstance()
            val yearCurent = c.get(Calendar.YEAR)
            return yearCurent-year
        } catch (e: ParseException) {
            e.printStackTrace()
            return 0
        }
    }
}
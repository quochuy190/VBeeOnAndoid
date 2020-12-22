package com.vn.vbeeon.common.intercepter

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 23:16
 * Version: 1.0
 */
class HttpHeaderDeviceInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       /* val preferencesHelper: PreferencesHelper =
            AppUtils.prctrApllication().appComponent.preferencesHelper()*/

        val request = chain.request().newBuilder()
            //.addHeader("Authorization", "Bearer " + preferencesHelper.getTokenDevice())
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .addHeader("Host", "parentalcontrol.vsmart.net")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("device_type", "2") // type device m
            .addHeader("Accept-Language", Locale.getDefault().language)
            .build()
        return chain.proceed(request)
    }
}
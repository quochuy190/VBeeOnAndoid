package com.vn.vbeeon.common.intercepter

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 23:21
 * Version: 1.0
 */
class ApiExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            /* try {
                throw new ApiException(response.code(), "error");
            } catch (ApiException e) {
                e.printStackTrace();
            }*/
        }
        return response
    }
}
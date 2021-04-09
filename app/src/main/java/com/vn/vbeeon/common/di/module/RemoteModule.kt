package com.vn.vbeeon.common.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vn.vbeeon.BuildConfig
import com.vn.vbeeon.R
import com.vn.vbeeon.common.intercepter.ApiExceptionInterceptor
import com.vn.vbeeon.common.intercepter.HttpHeaderDeviceInterceptor
import com.vn.vbeeon.common.intercepter.HttpHeaderInterceptor
import com.vn.vbeeon.common.intercepter.NullOnEmptyConverterFactory
import com.vn.vbeeon.data.remote.api.LoginApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 23:01
 * Version: 1.0
 */
@Module
class RemoteModule {

//    @Provides
//    @Singleton
//    fun provideLoginApi(retrofit: Retrofit): LoginApi {
//        return retrofit.create(LoginApi::class.java)
//    }

    @Provides
    @Singleton
    fun provideDevicesApi(@Named("no-auth") retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }
//
//    @Provides
//    @Singleton
//    internal fun provideCache(application: VBeeOnApplication): Cache {
//        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
//        val httpCacheDirectory = File(application.cacheDir, "http-cache")
//        return Cache(httpCacheDirectory, cacheSize)
//    }

    @Provides
    @Singleton
    @Named("no-auth")
    fun provideRetrofitNoAuth(application: Application): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(20, TimeUnit.SECONDS)
        httpClient.addInterceptor(HttpHeaderDeviceInterceptor())
        httpClient.addInterceptor(ApiExceptionInterceptor())
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        val gson: Gson =
            GsonBuilder().setDateFormat(ConstantCommon.ISO_8601_DATE_TIME_FORMAT_RECEIVE)
                .serializeNulls().create()
        return Retrofit.Builder()
            .baseUrl(application.resources.getString(R.string.api_base_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(context: Context): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(20, TimeUnit.SECONDS)
        httpClient.cache(null)
        httpClient.addInterceptor(HttpHeaderInterceptor())
     //   httpClient.addInterceptor(ApiExceptionInterceptor())
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }
        val gson =
            GsonBuilder().setDateFormat(ConstantCommon.ISO_8601_DATE_TIME_FORMAT_RECEIVE)
                .serializeNulls().create()
        return Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.api_base_url))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(httpClient.build())
            .build()
    }
}
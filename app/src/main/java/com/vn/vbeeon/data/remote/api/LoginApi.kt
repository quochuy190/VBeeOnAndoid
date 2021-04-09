package com.vn.vbeeon.data.remote.api

import com.vn.vbeeon.data.remote.entity.request.InitRequest
import com.vn.vbeeon.data.remote.entity.request.RegisterRequest
import com.vn.vbeeon.data.remote.entity.result.ApiResult
import com.vn.vbeeon.domain.model.User
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:34
 * Version: 1.0
 */

interface LoginApi {
    @POST("/users")
    fun apiInit(@Body userDto: InitRequest): Single<ApiResult<User>>

    @POST("/api/customer/register")
    fun register(@Body userDto: RegisterRequest): Single<ApiResult<Boolean>>


}
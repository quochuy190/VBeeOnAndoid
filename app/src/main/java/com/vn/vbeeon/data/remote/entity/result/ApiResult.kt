package com.vn.vbeeon.data.remote.entity.result

import com.google.gson.annotations.SerializedName

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:37
 * Version: 1.0
 */
class ApiResult<T> {
    @SerializedName("code")
    var errorCode = 0

    @SerializedName("msg")
    var errorMessage: String? = null

    @SerializedName("data")
    var data: T? = null
}
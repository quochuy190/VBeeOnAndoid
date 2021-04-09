package com.vn.vbeeon.data.remote.entity.request

data class RegisterRequest(
    val name: String?,
    var username: String,
    val password: String?,
    val type: String?,
    val country: String?,
    val district: String?,
    val phone: String,
    val address: String?,
    val email: String,
    val province: String?
)
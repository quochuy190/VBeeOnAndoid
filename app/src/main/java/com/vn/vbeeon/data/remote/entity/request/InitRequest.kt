package com.vn.vbeeon.data.remote.entity.request

data class InitRequest(val uuid: String, val deviceType: Int, val versionOs: String, val tokenKey:String, val appVersion: String,val languageId: Int)
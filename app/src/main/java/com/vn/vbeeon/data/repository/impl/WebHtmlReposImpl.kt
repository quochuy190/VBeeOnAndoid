package com.vn.vbeeon.data.repository.impl

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.data.repository.WebHtmlRepository
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.utils.AppUtils.readFile
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single

import javax.inject.Inject

class WebHtmlReposImpl @Inject constructor(): WebHtmlRepository {

    override fun getAllLisObjHtml(context: Context, fileName: String): List<ObjHtmlData> {
        val jsonString = context?.assets?.readFile(fileName)
        var gson = Gson()
        val objectList = gson.fromJson(jsonString, Array<ObjHtmlData>::class.java).asList()
        return objectList
    }

}
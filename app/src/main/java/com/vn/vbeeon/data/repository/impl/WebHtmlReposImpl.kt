package com.vn.vbeeon.data.repository.impl

import android.content.Context
import com.google.gson.Gson
import com.vn.vbeeon.data.repository.WebHtmlRepository
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.utils.AppUtils.readFile

import javax.inject.Inject

class WebHtmlReposImpl @Inject constructor(): WebHtmlRepository {

    override fun getAllLisObjHtml(context: Context, fileName: String): List<ObjHtmlData> {
        val jsonString = context?.assets?.readFile(fileName)
        var gson = Gson()
        val objectList = gson.fromJson(jsonString, Array<ObjHtmlData>::class.java).asList()
        return objectList
    }

}
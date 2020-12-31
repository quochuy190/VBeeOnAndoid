package com.vn.vbeeon.data.repository

import android.content.Context
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.domain.model.ObjHtmlData
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single

interface WebHtmlRepository {
    fun getAllLisObjHtml(context: Context, fileName: String) : List<ObjHtmlData>
}
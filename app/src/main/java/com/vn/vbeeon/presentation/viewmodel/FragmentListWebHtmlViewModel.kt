package com.vn.vbeeon.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.vn.vbeeon.data.repository.WebHtmlRepository
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class FragmentListWebHtmlViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repo: WebHtmlRepository

    public val listHtmlBase : MutableLiveData<List<ObjHtmlData>> = MutableLiveData()
    public val listHtmlCity : MutableLiveData<List<ObjHtmlData>> = MutableLiveData()
    public val listHtmlEnterp : MutableLiveData<List<ObjHtmlData>> = MutableLiveData()
    public val listHtmlGove : MutableLiveData<List<ObjHtmlData>> = MutableLiveData()
    init {
        Timber.e("init")
    }
    override fun onCleared() {
        super.onCleared()
    }
    private fun handleError(throwable: Throwable) {
        error.value = throwable
    }

    fun getListHtmlBase(context: Context,fileName : String) {
        listHtmlBase.postValue( repo.getAllLisObjHtml(context, fileName))
    }
    fun getListHtmlCitizen(context: Context,fileName : String) {
        listHtmlCity.postValue( repo.getAllLisObjHtml(context, fileName))
    }
    fun getListHtmlEnterprise(context: Context,fileName : String) {
        listHtmlEnterp.postValue( repo.getAllLisObjHtml(context, fileName))
    }
    fun getListHtmlGovernment(context: Context,fileName : String) {
        listHtmlGove.postValue( repo.getAllLisObjHtml(context, fileName))
    }
}
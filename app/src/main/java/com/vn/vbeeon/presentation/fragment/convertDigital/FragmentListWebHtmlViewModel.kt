package com.vn.vbeeon.presentation.fragment.convertDigital

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.repository.WebHtmlRepository
import com.vn.vbeeon.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class FragmentListWebHtmlViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repo: WebHtmlRepository

    init {
        Timber.e("init")
    }
    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }
    private fun handleError(throwable: Throwable) {
        error.value = throwable
    }

    fun getListHtml(context: Context,fileName : String) {
        repo.getAllLisObjHtml(context, fileName)
        Timber.d("test"+repo.getAllLisObjHtml(context, fileName).size)
    }
}
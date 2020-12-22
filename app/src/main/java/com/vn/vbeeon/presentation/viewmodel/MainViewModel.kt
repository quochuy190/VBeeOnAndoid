package com.vn.vbeeon.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.vn.vbeeon.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {


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
}
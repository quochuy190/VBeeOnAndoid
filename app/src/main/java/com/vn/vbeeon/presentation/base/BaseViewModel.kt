package com.vn.vbeeon.presentation.base

import androidx.annotation.IntDef
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

@IntDef(
    BaseViewModel.RQ_START,
    BaseViewModel.RQ_FINISH
)
annotation class Status

abstract class BaseViewModel : ViewModel() {
    companion object {
        const val RQ_START = 0x00
        const val RQ_FINISH = 0x01
    }

    protected val disposables: CompositeDisposable = CompositeDisposable()

    val status = MutableLiveData<@Status Int>()

    val error = MutableLiveData<Throwable>()

    private val loading = MutableLiveData<Boolean>()

    open fun getLoading(): MutableLiveData<Boolean> {
        return loading
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}
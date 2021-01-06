package com.vn.vbeeon.presentation.viewmodel

import com.vn.vbeeon.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 04-January-2021
 * Time: 22:25
 * Version: 1.0
 */
class SphygmomanometerViewModel @Inject constructor() : BaseViewModel() {
    init {
        Timber.e("init")
    }
    override fun onCleared() {
        super.onCleared()
    }
}
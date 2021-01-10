package com.vn.vbeeon.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.domain.model.Device
import com.vn.vbeeon.domain.model.convertFromEntityList
import com.vn.vbeeon.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var repoDevice : DeviceRepository

    val devicesRes : MutableLiveData<List<Device>> = MutableLiveData()

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
    fun loadDevices(){
        devicesRes.postValue(convertFromEntityList(repoDevice.getAllListDevice()))
    }
}
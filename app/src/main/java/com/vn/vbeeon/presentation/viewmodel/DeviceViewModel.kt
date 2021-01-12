package com.vn.vbeeon.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.vn.vbeeon.data.repository.DeviceRepository
//import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.domain.model.Device
import com.vn.vbeeon.domain.model.convertToEntity
import com.vn.vbeeon.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class DeviceViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repo: DeviceRepository

    public val listDeviceRes : MutableLiveData<List<Device>> = MutableLiveData()
    init {
        Timber.e("init")
    }
    override fun onCleared() {
        super.onCleared()
    }
    private fun handleError(throwable: Throwable) {
        error.value = throwable
    }

    fun getListDevice() {
        listDeviceRes.postValue( repo.createDevice())
    }
    fun saveDevice(device: Device){
        repo.insertDevice(convertToEntity(device))
    }

}
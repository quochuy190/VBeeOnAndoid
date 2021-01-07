package com.vn.vbeeon.domain.usecase

import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.domain.model.Device
import com.vn.vbeeon.domain.model.convertFromEntityList
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CreateDeviceUseCase @Inject constructor() {
    @Inject
    lateinit var deviceRepos: DeviceRepository

//    fun exe(): Single<List<Device>> {
//        return deviceRepos.getAllListDevice()
//            .map({ deviceEntity ->
//                convertFromEntityList(deviceEntity)
//            })
//    }

}
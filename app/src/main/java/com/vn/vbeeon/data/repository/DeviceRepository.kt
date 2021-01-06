package com.vn.vbeeon.data.repository

import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.domain.model.Device
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single

interface DeviceRepository {
    fun createDevice() : List<Device>
    fun insertDevice(device: DeviceEntity) : Single<Boolean>
    fun getAllListDevice() : Single<List<DeviceEntity>>
    fun getDeviceById(id: Int) : Single<DeviceEntity>
    fun deleteDeviceById(id: Int): Single<Boolean>
    fun updateDevice(device: DeviceEntity) : Single<Boolean>

}
package com.vn.vbeeon.data.repository

import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.local.entity.UserEntity
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single

interface DeviceRepository {
    fun createUser(device : DeviceEntity) : Single<Boolean>
    fun getAllListDevice() : Single<List<DeviceEntity>>
    fun getDeviceById(id: Int) : Single<DeviceEntity>
    fun deleteDeviceById(id: Int): Single<Boolean>
    fun updateDevice(device: DeviceEntity) : Single<Boolean>

}
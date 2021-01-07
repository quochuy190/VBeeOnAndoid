package com.vn.vbeeon.data.repository

import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.domain.model.Device
import io.reactivex.rxjava3.core.Single

interface DeviceRepository {
    fun createDevice() : List<Device>
    fun insertDevice(device: DeviceEntity) : Boolean
    fun getAllListDevice() : List<DeviceEntity>
    fun getALL() : List<DeviceEntity>
    fun getDeviceById(id: Int) : DeviceEntity
    fun deleteDeviceById(id: Int): Boolean
    fun updateDevice(device: DeviceEntity) : Boolean

}
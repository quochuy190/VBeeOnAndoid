package com.vn.vbeeon.data.repository.impl

import com.vn.vbeeon.data.local.dao.DeviceDao
import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.data.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DeviceReposImpl @Inject constructor(): DeviceRepository {
    @Inject
    lateinit var deviceDao: DeviceDao

    override fun createUser(device: DeviceEntity): Single<Boolean> {
        return deviceDao.insertDevice(device)
    }

    override fun getAllListDevice(): Single<List<DeviceEntity>> {
        return deviceDao.loadAllDevice()
    }

    override fun getDeviceById(id: Int): Single<DeviceEntity> {
        return deviceDao.loadDeviceById(id)
    }

    override fun deleteDeviceById(id: Int): Single<Boolean> {
        return deviceDao.delete(id)
    }

    override fun updateDevice(device: DeviceEntity): Single<Boolean> {
        return deviceDao.updatetoDao(device)
    }


}
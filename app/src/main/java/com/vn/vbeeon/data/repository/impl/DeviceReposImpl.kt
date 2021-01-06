package com.vn.vbeeon.data.repository.impl

import com.vn.vbeeon.R
import com.vn.vbeeon.VBeeOnApplication
import com.vn.vbeeon.data.local.dao.DeviceDao
import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.domain.model.Device
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.android.synthetic.main.item_device_new.view.*
import javax.inject.Inject

class DeviceReposImpl @Inject constructor() : DeviceRepository {
    @Inject
    lateinit var deviceDao: DeviceDao


    override fun createDevice(): List<Device> {
        var listDevice: MutableList<Device> = mutableListOf()
        listDevice.add(
            Device(
                0,
                VBeeOnApplication.instance.getString(R.string.device_name_sphyg),
                R.drawable.ic_source,
                0,
                VBeeOnApplication.instance.getString(R.string.category_name_sphyg)
            )
        )
        listDevice.add(
            Device(
                1,
                VBeeOnApplication.instance.getString(R.string.device_name_obd),
                R.drawable.ic_source,
                1,
                VBeeOnApplication.instance.getString(R.string.category_name_obd)
            )
        )
        return listDevice
    }

    override fun insertDevice(device: DeviceEntity): Single<Boolean> {
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
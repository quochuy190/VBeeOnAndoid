package com.vn.vbeeon.data.repository.impl

import com.vn.vbeeon.R
import com.vn.vbeeon.VBeeOnApplication
import com.vn.vbeeon.data.local.dao.DeviceDao
import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.domain.model.Device
import io.reactivex.rxjava3.core.Single

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevicesReposImpl @Inject constructor(): DeviceRepository {

    @Inject
    lateinit var deviceDao: DeviceDao

    override fun createDevice(): List<Device> {
        var listDevice: MutableList<Device> = mutableListOf()
        listDevice.add(
            Device(
                0,
                VBeeOnApplication.instance.getString(R.string.device_name_sphyg),
                R.drawable.img_device_sphyg,
                0,
                VBeeOnApplication.instance.getString(R.string.category_name_sphyg), false, "https://vbeeon.com/may-do-huyet-ap",
                "Là thiết bị tích hợp công nghệ Bluetooth giúp theo dõi," +
                        " tầm soát huyết áp từ xa thuận lợi, hiệu quả qua ứng dụng trên smartphone"
            )
        )
        listDevice.add(
            Device(
                1,
                VBeeOnApplication.instance.getString(R.string.device_name_obd),
                R.drawable.img_device_obd,
                1,
                VBeeOnApplication.instance.getString(R.string.category_name_obd), false, "https://vbeeon.com/thiet-bi-obd-basic",
                "Là thiết bị cần thiết để kết nối các thiết bị thông minh lên hệ thống và điều khiển các thiết bị qua ứng dụng trên smartphone." +
                        "Nếu bạn đã có iVIG thì xin hay cắm vào trên công OBD-II trên xe hơi của bạn."
            )
        )
        return listDevice
    }

    override fun insertDevice(device: DeviceEntity): Boolean {
        deviceDao.insertDevice(device)
        return true
    }

    override fun getAllListDevice(): List<DeviceEntity>{
        return deviceDao.loadAllDevice()
    }

    override fun getALL(): List<DeviceEntity> {
        return deviceDao.loadAllDeviceL()
    }

    override fun getDeviceById(id: Int): DeviceEntity {
        return deviceDao.loadDeviceById(id)
    }

    override fun deleteDeviceById(id: Int): Boolean {
        deviceDao.delete(id)
        return true
    }

    override fun updateDevice(device: DeviceEntity): Boolean {
        deviceDao.updatetoDao(device)
        return true
    }


}
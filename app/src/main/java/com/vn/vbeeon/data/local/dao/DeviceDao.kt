package com.vn.vbeeon.data.local.dao

import androidx.room.*
import com.vn.vbeeon.data.local.entity.DeviceEntity
import io.reactivex.rxjava3.core.Single

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:25
 * Version: 1.0
 */
@Dao interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDevice(user : DeviceEntity)

    @Query("SELECT * FROM device")
    fun loadAllDevice(): List<DeviceEntity>

    @Query("SELECT * FROM device")
    fun loadAllDeviceL(): List<DeviceEntity>

    @Query("SELECT * FROM device WHERE device_id IN (:id)")
    fun loadDeviceById(id: Int): DeviceEntity

    @Query("DELETE FROM device WHERE device_id = :id")
    fun delete(id: Int)

    @Update
    fun updatetoDao(deviceEntity: DeviceEntity?)
}
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
@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListDevices(users : List<DeviceEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDevice(user : DeviceEntity): Single<Boolean>

    @Query("SELECT * FROM device")
    fun loadAllDevice(): Single<List<DeviceEntity>>

    @Query("SELECT * FROM device WHERE device_id IN (:id)")
    fun loadDeviceById(id: Int): Single<DeviceEntity>

    @Query("DELETE FROM device WHERE device_id = :id")
    fun delete(id: Int): Single<Boolean>

    @Update
    fun updatetoDao(deviceEntity: DeviceEntity?): Single<Boolean>
}
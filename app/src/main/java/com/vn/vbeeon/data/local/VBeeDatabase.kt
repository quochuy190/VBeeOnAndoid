package com.vn.vbeeon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vn.vbeeon.data.local.dao.DeviceDao
import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.local.entity.UserEntity


/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:14
 * Version: 1.0
 */
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class VBeeDatabase : RoomDatabase(){
    abstract fun userDao() : UserDao
    abstract fun deviceDao() : DeviceDao
}
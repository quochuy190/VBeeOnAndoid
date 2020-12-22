package com.vn.vbeeon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.vn.vbeeon.data.local.entity.UserEntity
import io.reactivex.rxjava3.core.Single

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:25
 * Version: 1.0
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users : List<UserEntity>): LongArray
    fun insertUserObj(user : UserEntity): Single<Boolean>
}
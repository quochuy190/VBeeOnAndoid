package com.vn.vbeeon.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:16
 * Version: 1.0
 */
@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val id: Int,
    @ColumnInfo(name = "user_name")
    var name: String?,
    @ColumnInfo(name = "user_birthday")
    var birthDay : String?
)
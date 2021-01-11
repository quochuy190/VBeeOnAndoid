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
@Entity(tableName = "device")
data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo(name = "device_id")
    val id: Int,
    @ColumnInfo(name = "device_name")
    val name: String,
    @ColumnInfo(name = "int_source")
    val intSource: Int,
    @ColumnInfo(name = "category_id")
    val categoryID: Int,//1: thiết bị đo, 2: thiết bị trong xe hơi
    @ColumnInfo(name = "category_name")
    val categoryName: String,
    @ColumnInfo(name = "status")
    var isStatus: Boolean = false,
    @ColumnInfo(name = "titel_detail")
    val titelDetail: String,
    @ColumnInfo(name = "des")
    val desCription: String
)
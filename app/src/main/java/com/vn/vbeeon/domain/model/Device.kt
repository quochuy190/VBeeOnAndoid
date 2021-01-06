package com.vn.vbeeon.domain.model

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
data class Device(
    val id: Int,
    var name: String?,
    var intSource: Int,
    var categoryID: Int,//1: thiết bị đo, 2: thiết bị trong xe hơi
    var categoryName: String

)
package com.vn.vbeeon.domain.model

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.local.entity.UserEntity

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:16
 * Version: 1.0
 */
data class User(
    val id: Int,
    var name: String?,
    @DrawableRes val resId: Int = 0,
    var birthDay: String,
)

fun convertFromEntityList(userEntity: List<User>):List<User>{
    var listUser : MutableList<User> = mutableListOf()
    userEntity.forEach {
        listUser.add(User(it.id, it.name, it.resId, it.birthDay))
    }
    return listUser
}
//fun convertToEntity(user: User) : UserEntity{
//    return UserEntity(user.id, user.name, user.resId, user.birthDay)
//
//}

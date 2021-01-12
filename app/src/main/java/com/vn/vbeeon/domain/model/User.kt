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
    val name: String,
    @DrawableRes val resId: Int = 0,
    val birthDay: String,
)

fun convertUserFromListEntity(userEntity: List<UserEntity>):List<User>{
    var listUser : MutableList<User> = mutableListOf()
    userEntity.forEach {
        listUser.add(User(it.id, it.name, 0, it.birthDay))
    }
    return listUser
}
fun convertUserFromEntityObj(it: UserEntity) : User{

    return User(it.id, it.name, 0, it.birthDay)
}
fun convertToEntityObj(user: User) : UserEntity{
    return UserEntity(user.id, user.name, user.birthDay)

}

package com.vn.vbeeon.data.repository

import com.vn.vbeeon.data.local.entity.UserEntity
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single

interface UserRepository {
  //  fun createUser(user : UserEntity) : Single<Boolean>
    fun createUserTest(user : UserEntity) : Boolean
}
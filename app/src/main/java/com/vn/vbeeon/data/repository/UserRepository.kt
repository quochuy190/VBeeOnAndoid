package com.vn.vbeeon.data.repository

import com.google.android.gms.common.api.ApiException
import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.remote.entity.request.InitRequest
import com.vn.vbeeon.data.remote.entity.request.RegisterRequest
import com.vn.vbeeon.domain.model.User
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single

interface UserRepository {
  //  fun createUser(user : UserEntity) : Single<Boolean>
    fun createUserTest(user : UserEntity) : Boolean
  fun getAllListUser() : List<UserEntity>
  fun getUserById(id: Int) : UserEntity
  fun deleteUserById(id: Int): Boolean
  fun updateUser(device: UserEntity) : Boolean
  fun initApi(user: InitRequest): Single<User>
  fun registerApi(user: RegisterRequest): Single<Boolean>

}
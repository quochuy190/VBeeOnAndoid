package com.vn.vbeeon.data.repository.impl

import android.util.Log
import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.remote.api.LoginApi
import com.vn.vbeeon.data.remote.entity.ApiException
import com.vn.vbeeon.data.remote.entity.ErrorCode
import com.vn.vbeeon.data.remote.entity.request.InitRequest
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.domain.model.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserReposImpl @Inject constructor() : UserRepository {
    @Inject
    lateinit var userDao: UserDao
    @Inject
    lateinit var loginApi: LoginApi

//    override fun createUser(user: UserEntity): Single<Boolean> {
//        return userDao.insertUserObj(user)
//    }

    override fun createUserTest(user: UserEntity): Boolean {
        userDao.insertUserObj(user)
        return true
    }

    override fun getAllListUser(): List<UserEntity> {
        return userDao.loadAllUser()
    }

    override fun getUserById(id: Int): UserEntity {
        return userDao.loadUserById(id)
    }

    override fun deleteUserById(id: Int): Boolean {
        userDao.deleteAll()
        return true
    }

    override fun updateUser(device: UserEntity): Boolean {
        userDao.updatetoDao(device)
        return true
    }

//    override fun initApi(user: InitRequest): Single<User> {
//        return loginApi.apiInit(user).map({ response ->
//            if (response.errorCode == ErrorCode.SUCCESS) {
//                return@map response.data
//            } else {
//                throw ApiException(response.errorCode, response.errorMessage)
//            }
//        })
//    }



}
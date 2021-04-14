package com.vn.vbeeon.data.repository.impl

import android.util.Log
import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.remote.api.LoginApi
import com.vn.vbeeon.data.remote.entity.ApiException
import com.vn.vbeeon.data.remote.entity.ErrorCode
import com.vn.vbeeon.data.remote.entity.request.InitRequest
import com.vn.vbeeon.data.remote.entity.request.LoginRequest
import com.vn.vbeeon.data.remote.entity.request.RegisterRequest
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.domain.model.User
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
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

    override fun initApi(user: InitRequest): Single<User> {
        return loginApi.apiInit(user).map({ response ->
            if (response.errorCode == ErrorCode.SUCCESS) {
                return@map response.data
            } else {
                throw ApiException(response.errorCode, response.errorMessage)
            }
        })
    }

    override fun registerApi(user: RegisterRequest): Single<Boolean> {
        return loginApi.register(user).map({ response ->
//            Timber.e(""+response.errorCode)
//            if (response.errorCode == ErrorCode.SUCCESS) {
//                Timber.e(""+response.errorCode)
//                return@map response.data
//            } else {
//                Timber.e(""+response.errorCode)
//                throw ApiException(response.errorCode, response.errorMessage)
//            }
            return@map if (response.errorCode === ErrorCode.SUCCESS || response.errorCode === ErrorCode.ERROR_NOT_EXITS) {
                true
            } else {
                throw ApiException(response.errorCode, response.errorMessage)
            }
        })
    }

    override fun loginApi(login: LoginRequest): Single<Boolean> {
        return loginApi.login(login).map({ response ->
            return@map if (response.errorCode === ErrorCode.SUCCESS || response.errorCode === ErrorCode.ERROR_NOT_EXITS) {
                Log.d("TAG", "loginApi: "+response)
                true
            } else {
                throw ApiException(response.errorCode, response.errorMessage)
            }
        })
    }


}
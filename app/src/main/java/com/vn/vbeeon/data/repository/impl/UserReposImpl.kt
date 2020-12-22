package com.vn.vbeeon.data.repository.impl

import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserReposImpl @Inject constructor(): UserRepository {
    @Inject
    lateinit var userDao: UserDao
    override fun createUser(user: UserEntity): Single<Boolean> {
        return userDao.insertUserObj(user)
    }


}
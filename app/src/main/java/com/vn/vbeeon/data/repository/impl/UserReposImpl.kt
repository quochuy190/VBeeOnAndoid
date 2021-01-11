package com.vn.vbeeon.data.repository.impl

import com.vn.vbeeon.data.local.dao.UserDao
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.repository.UserRepository
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserReposImpl @Inject constructor() : UserRepository {
    @Inject
    lateinit var userDao: UserDao
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


}
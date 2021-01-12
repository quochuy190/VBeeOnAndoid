package com.vn.vbeeon.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.domain.model.*
//import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.presentation.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class UserViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var repo: UserRepository

    public val userRes : MutableLiveData<User> = MutableLiveData()
    public val listUserRes : MutableLiveData<List<User>> = MutableLiveData()
    init {
        Timber.e("init")
    }
    override fun onCleared() {
        super.onCleared()
    }
    private fun handleError(throwable: Throwable) {
        error.value = throwable
    }

    fun getUser() {
        userRes.postValue(convertUserFromEntityObj(repo.getUserById(0)))
    }
    fun getListUser() {
        listUserRes.postValue(convertUserFromListEntity(repo.getAllListUser()))
    }
    fun saveUser(user: User){
        repo.createUserTest(convertToEntityObj(user))
    }

}
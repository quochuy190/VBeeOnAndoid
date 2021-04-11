package com.vn.vbeeon.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.vn.vbeeon.data.local.entity.DeviceEntity
import com.vn.vbeeon.data.local.entity.UserEntity
import com.vn.vbeeon.data.remote.api.LoginApi
import com.vn.vbeeon.data.remote.entity.request.LoginRequest
import com.vn.vbeeon.data.remote.entity.request.RegisterRequest
import com.vn.vbeeon.data.remote.entity.result.ApiResult
import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.domain.model.Device
import com.vn.vbeeon.domain.model.User
import com.vn.vbeeon.domain.model.convertFromEntityList
import com.vn.vbeeon.domain.usecase.CreateDeviceUseCase
import com.vn.vbeeon.domain.usecase.RegisterUseCase
import com.vn.vbeeon.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class LoginViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var repoDevice : DeviceRepository

    @Inject
    lateinit var registerUseCase: RegisterUseCase

    @Inject
    lateinit var loginApi: LoginApi

    val devicesRes : MutableLiveData<List<Device>> = MutableLiveData()
    val response = MutableLiveData<Boolean>()
    init {
        Timber.e("init")
    }
    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }
    private fun handleError(throwable: Throwable) {
        Timber.e("throwable.hashCode "+throwable.hashCode())
        Timber.e("throwable.cause "+throwable.cause)
        when (throwable) {
            is ConnectException, is UnknownHostException ->{
                // ResponseResult.error<T>(BaseApplication.getStringResource(R.string.message_connect_exception))
            }
            is SocketTimeoutException ->{
               // ResponseResult.error<T>(BaseApplication.getStringResource(R.string.message_socket_timeout_exception))
            }
            is HttpException -> {
                val code = throwable.code()
                when(code){
                    HttpsURLConnection.HTTP_UNAUTHORIZED ->{

                    }
                    HttpsURLConnection.HTTP_BAD_METHOD ->{
                        val contentError = throwable.response()?.errorBody()!!.string()
                        val responseError = Gson().fromJson(contentError, ApiResult::class.java)
                        Timber.e("HTTP_UNAUTHORIZED"+responseError.errorMessage)
                        Timber.e("HTTP_BAD_METHOD")
                    }
                    HttpsURLConnection.HTTP_BAD_REQUEST ->{
                        Timber.e("HTTP_BAD_REQUEST")
                        val contentError = throwable.response()?.errorBody()!!.string()
                        val responseError = Gson().fromJson(contentError, ApiResult::class.java)
                        Timber.e("HTTP_UNAUTHORIZED"+responseError.errorMessage)
                        Timber.e("HTTP_BAD_METHOD")
                    }
                }
            }
        }

        getLoading().postValue(false)
        error.value = throwable
    }

    fun register(request: RegisterRequest){
        //devicesRes.postValue(convertFromEntityList(repoDevice.getAllListDevice()))
        registerUseCase.exe(request) .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { t -> getLoading().postValue(true) }
            .subscribe(
                {
                    getLoading().postValue(false)
                    response.value = it
                }, this::handleError
            )

    }

    fun login(request: LoginRequest){
        //devicesRes.postValue(convertFromEntityList(repoDevice.getAllListDevice()))
        userRepository.loginApi(request) .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { t -> getLoading().postValue(true) }
            .subscribe(
                {
                    getLoading().postValue(false)
                    response.value = it
                }, this::handleError
            )

    }
    fun deleteDevice(device: Device){
        repoDevice.deleteDeviceById(device.id)
        devicesRes.postValue(convertFromEntityList(repoDevice.getAllListDevice()))
    }

}
package com.vn.vbeeon.domain.usecase

import android.os.Build
import com.vn.vbeeon.BuildConfig
import com.vn.vbeeon.data.remote.entity.request.InitRequest
import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.domain.model.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CreateDeviceUseCase @Inject constructor() {
    @Inject
    lateinit var userRepos: UserRepository

//    fun exe(): Single<List<Device>> {
//        return deviceRepos.getAllListDevice()
//            .map({ deviceEntity ->
//                convertFromEntityList(deviceEntity)
//            })
//    }
//open fun exe(uuid: String?): Single<User> {
//    val request = InitRequest("uuid", 2, ""+ Build.VERSION.SDK_INT, "abctest", ""+ BuildConfig.VERSION_NAME,1);
//   return userRepos.initApi(request)
//}
}

//var user = User(0, "", 0, "", 0, 10)
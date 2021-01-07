package com.vn.vbeeon.common.di.module

import com.vn.vbeeon.data.repository.DeviceRepository
import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.data.repository.WebHtmlRepository
import com.vn.vbeeon.data.repository.impl.DevicesReposImpl
import com.vn.vbeeon.data.repository.impl.UserReposImpl
import com.vn.vbeeon.data.repository.impl.WebHtmlReposImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepos(userReposImpl: UserReposImpl): UserRepository {
        return userReposImpl
    }
    @Provides
    @Singleton
    fun provideWebHtmlRepos(webHtmlReposImpl: WebHtmlReposImpl): WebHtmlRepository {
        return webHtmlReposImpl
    }

    @Provides
    @Singleton
    fun provideDeviceRepos(deviceReposImpl: DevicesReposImpl): DeviceRepository {
        return deviceReposImpl
    }
}
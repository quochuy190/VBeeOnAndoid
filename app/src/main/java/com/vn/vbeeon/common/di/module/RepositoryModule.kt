package com.vn.vbeeon.common.di.module

import com.vn.vbeeon.data.repository.UserRepository
import com.vn.vbeeon.data.repository.impl.UserReposImpl
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
}
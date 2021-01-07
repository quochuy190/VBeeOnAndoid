package com.vn.vbeeon.common.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.vn.vbeeon.data.local.VBeeDatabase
import com.vn.vbeeon.data.local.dao.DeviceDao
import com.vn.vbeeon.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:58
 * Version: 1.0
 */

@Module
class LocalModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): VBeeDatabase {
        return Room.databaseBuilder(application, VBeeDatabase::class.java, "VBeeOn.db")
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideUserDao(appDatabase: VBeeDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    internal fun provideDeviceDao(appDatabase: VBeeDatabase): DeviceDao {
        return appDatabase.deviceDao()
    }
}
package com.vn.vbeeon
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.di.component.DaggerAppComponent
import timber.log.Timber

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-December-2020
 * Time: 22:00
 * Version: 1.0
 */
class VBeeOnApplication : Application() {
    companion object {
        lateinit var instance: VBeeOnApplication
            private set
    }

    lateinit var appComponent: AppComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        //AppInitializeManager.init(this)
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
            .bindContext(this)
            ?.build()!!
    }

}
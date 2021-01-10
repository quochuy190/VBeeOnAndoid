package com.vn.vbeeon.common.di.component

import android.app.Application
import android.content.Context
import com.vn.vbeeon.VBeeOnApplication
import com.vn.vbeeon.common.di.module.LocalModule
import com.vn.vbeeon.common.di.module.RemoteModule
import com.vn.vbeeon.common.di.module.RepositoryModule
import com.vn.vbeeon.common.di.module.ViewModelModule
import com.vn.vbeeon.presentation.fragment.DemoFragment
import com.vn.vbeeon.presentation.fragment.HomePageFragment
import com.vn.vbeeon.presentation.fragment.MainFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.*
import com.vn.vbeeon.presentation.fragment.deviceAddNew.DeviceListNewFragment
import com.vn.vbeeon.presentation.fragment.sphygmomanometer.SphygHomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RemoteModule::class,
        ViewModelModule::class,
        LocalModule::class,
        RepositoryModule::class,
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(application: VBeeOnApplication)
    fun inject(mainFragment: MainFragment)
    fun inject(demoFragment: DemoFragment)
    fun inject(homePageFragment: HomePageFragment)
    fun inject(fragment: ConvertDigitalPageFragment)
    fun inject(fragment: ConvertDigitalDetailFragment)
    fun inject(fragment: ListDocConverDigitalFragment)
    fun inject(fragment: WebViewFragment)
    fun inject(fragment: SphygHomeFragment)
    fun inject(fragment: FragmentVBeeOnMission)
    fun inject(fragment: DeviceListNewFragment)
    fun inject(fragment: FragmentVbeeonITForLife)

//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun bindContext(context: Context?): Builder?
//        fun build(): AppComponent?
//    }
}
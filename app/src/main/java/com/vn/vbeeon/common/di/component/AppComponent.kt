package com.vn.vbeeon.common.di.component

import android.content.Context
import com.vn.vbeeon.common.di.module.LocalModule
import com.vn.vbeeon.common.di.module.RemoteModule
import com.vn.vbeeon.common.di.module.RepositoryModule
import com.vn.vbeeon.common.di.module.ViewModelModule
import com.vn.vbeeon.presentation.fragment.DemoFragment
import com.vn.vbeeon.presentation.fragment.HomePageFragment
import com.vn.vbeeon.presentation.fragment.MainFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.ConvertDigitalDetailFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.ConvertDigitalPageFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.ListDocConverDigitalFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.WebViewFragment
import com.vn.vbeeon.presentation.fragment.sphygmomanometer.SphygHomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RemoteModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        LocalModule::class]
)
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(demoFragment: DemoFragment)
    fun inject(homePageFragment: HomePageFragment)
    fun inject(fragment: ConvertDigitalPageFragment)
    fun inject(fragment: ConvertDigitalDetailFragment)
    fun inject(fragment: ListDocConverDigitalFragment)
    fun inject(fragment: WebViewFragment)
    fun inject(fragment: SphygHomeFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(context: Context?): Builder?
        fun build(): AppComponent?
    }
}
package com.vn.vbeeon.common.di.component

import android.content.Context
import com.vn.vbeeon.common.di.module.LocalModule
import com.vn.vbeeon.common.di.module.RemoteModule
import com.vn.vbeeon.common.di.module.RepositoryModule
import com.vn.vbeeon.common.di.module.ViewModelModule
import com.vn.vbeeon.presentation.fragment.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
       RemoteModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    LocalModule::class
    ]
)
interface AppComponent {
    fun inject(mainFragment: MainFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(context: Context?): Builder?
        fun build(): AppComponent?
    }
}
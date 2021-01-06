package com.vn.vbeeon.common.di.module
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vn.vbeeon.common.util.ViewModelKey
import com.vn.vbeeon.presentation.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(userViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FragmentListWebHtmlViewModel::class)
    abstract fun bindFragmentListWebviewViewModel(listWebViewModel: FragmentListWebHtmlViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SphygmomanometerViewModel::class)
    abstract fun bindSphygViewModel(listWebViewModel: SphygmomanometerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeviceViewModel::class)
    abstract fun bindDeviceViewModel(deviceViewModel: DeviceViewModel): ViewModel

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
package com.vn.vbeeon.presentation.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.activity.SphygmomanometerActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home_page.*
import timber.log.Timber


@Suppress("DEPRECATION")
class HomePageFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_page
    }

    override fun initView() {
        btn_add_device_empty_list.setOnSafeClickListener {
            context?.launchActivity<SphygmomanometerActivity>()
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
            MainViewModel::class.java)
        mainViewModel.loadDevices()
    }

    override fun observable() {
        mainViewModel.devicesRes.observe(context as MainActivity, Observer {
            if (it.size>0){
                Timber.d("size ${it.size}")
            }else{
                Timber.d("size 0")
            }
        })
    }


}
package com.vn.vbeeon.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.DeviceAddNewActivity
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import com.vsm.ambientmode.ui.timer.DeviceHomeAdapter
import kotlinx.android.synthetic.main.fragment_home_page.*
import timber.log.Timber


@Suppress("DEPRECATION")
class HomePageFragment : BaseFragment() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: DeviceHomeAdapter
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
            context?.launchActivity<DeviceAddNewActivity>()
        }
        mAdapter = DeviceHomeAdapter(onClick = {position, item ->

        }, onClickDelete = {
            Timber.d("")
        })
        rvListDevice.apply { adapter = mAdapter }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
            MainViewModel::class.java)
        mainViewModel.loadDevices()
    }

    override fun observable() {
        mainViewModel.devicesRes.observe(context as MainActivity, Observer {
            if (it.size>0){
                viewEmtryDevice.visibility = View.GONE
                mAdapter.setData(it)
                Timber.d("size ${it.size}")
            }else{
                viewEmtryDevice.visibility = View.VISIBLE
            }
        })
    }

}
package com.vn.vbeeon.presentation.fragment.deviceAddNew

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.presentation.activity.DeviceAddNewActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.DeviceViewModel
import com.vsm.ambientmode.ui.timer.DeviceAddNewAdapter
import kotlinx.android.synthetic.main.fragment_add_new_device.*


@Suppress("DEPRECATION")
class DeviceListNewFragment : BaseFragment() {
    private lateinit var adapterDevice: DeviceAddNewAdapter
    lateinit var mViewModel: DeviceViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_add_new_device
    }

    override fun initView() {
        adapterDevice = DeviceAddNewAdapter() { position, item ->
            //(context as ConvertDigitalActivity).openFragment(WebViewFragment.newInstance(item.content), true)
        }
        rvDeviceNew.apply { adapter = adapterDevice }
    }

    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(activity as DeviceAddNewActivity, viewModelFactory).get(
            DeviceViewModel::class.java)
        mViewModel.getListDevice()
    }

    override fun observable() {
        mViewModel.listDeviceRes.observe(this, Observer {
            adapterDevice.setData(it)
        })
    }


}
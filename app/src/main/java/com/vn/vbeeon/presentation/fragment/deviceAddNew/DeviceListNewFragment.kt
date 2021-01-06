package com.vn.vbeeon.presentation.fragment.deviceAddNew

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.presentation.activity.ConvertDigitalActivity
import com.vn.vbeeon.presentation.activity.DeviceAddNewActivity
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.adapter.MainViewPagerAdapter
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.WebViewFragment
import com.vn.vbeeon.presentation.viewmodel.DeviceViewModel
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import com.vsm.ambientmode.ui.timer.DeviceAddNewAdapter
import com.vsm.ambientmode.ui.timer.HtmlAdapter
import kotlinx.android.synthetic.main.fragment_list_doc_convert_digital.*


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
        rvListHtml.apply { adapter = adapterDevice }
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
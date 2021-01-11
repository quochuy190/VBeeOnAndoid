package com.vn.vbeeon.presentation.fragment.deviceAddNew

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.DeviceAddNewActivity
import com.vn.vbeeon.presentation.adapter.deviceAddNew.DeviceAdapter
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.WebViewFragment
import com.vn.vbeeon.presentation.viewmodel.DeviceViewModel
import com.vsm.ambientmode.ui.timer.DeviceAddNewAdapter
import kotlinx.android.synthetic.main.fragment_add_new_device.*
import kotlinx.android.synthetic.main.fragment_add_new_device.txt_link
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class DeviceListNewFragment : BaseFragment() {
    private lateinit var adapterDevice: DeviceAdapter
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
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "DANH SÁCH THIẾT BỊ"
        adapterDevice = DeviceAdapter(doneClick = {
            (context as FragmentActivity).openFragment(DeviceDetailFragment.newInstance(it), true)
        }, onClickDetail = {
            (context as FragmentActivity).openFragment(WebViewFragment.newInstance(it.titelDetail,
                ConstantCommon.KEY_WEBVIEW_URL), true)
        })
        rvDeviceNew.apply { adapter = adapterDevice }
        txt_link.setOnSafeClickListener {
            context?.launchActivity<com.vn.vbeeon.presentation.activity.FragmentActivity>{
                putExtra(ConstantCommon.KEY_SEND_OPTION_FRAGMENT, ConstantCommon.KEY_SEND_WEBVIEW_VBEEON_SP)
            }
        }
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
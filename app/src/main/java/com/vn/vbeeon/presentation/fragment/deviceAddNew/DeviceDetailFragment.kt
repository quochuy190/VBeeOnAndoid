package com.vn.vbeeon.presentation.fragment.deviceAddNew

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.domain.model.Device
import com.vn.vbeeon.presentation.activity.DeviceAddNewActivity
import com.vn.vbeeon.presentation.activity.FragmentActivity
import com.vn.vbeeon.presentation.activity.SphygmomanometerActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.FragmentListWebHtmlViewModel
import kotlinx.android.synthetic.main.fragment_device_detail.*
import kotlinx.android.synthetic.main.fragment_webv.*
import kotlinx.android.synthetic.main.item_device_local.*
import kotlinx.android.synthetic.main.item_device_new.view.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon

@Suppress("DEPRECATION")
class DeviceDetailFragment : BaseFragment() {
    lateinit var mViewModel: FragmentListWebHtmlViewModel
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    companion object {
        fun newInstance(content: Device): DeviceDetailFragment {
            val fragment = DeviceDetailFragment()
            val args = Bundle()
            args.putSerializable("device", content)
            fragment.setArguments(args)
            return fragment
        }
    }

    lateinit var device: Device;
    var key: String = "";
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getSerializable("device")?.let {
            device = it as Device
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_device_detail
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = device.categoryName
        context?.let { Glide.with(it).load(device.intSource).into(imgDeviceDetail) }
        tvDesDeviceDetail.text = device.desCription
        tvNameDeviceDetail.text = device.name

        btnSearchDevice.setOnSafeClickListener {
            context?.launchActivity<SphygmomanometerActivity>()
        }
    }

    override fun initViewModel() {
        mViewModel =
            ViewModelProviders.of(activity as DeviceAddNewActivity, viewModelFactory).get(
                FragmentListWebHtmlViewModel::class.java
            )

    }

    override fun observable() {

    }
}
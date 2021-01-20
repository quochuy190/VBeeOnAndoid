package com.vn.vbeeon.presentation.fragment.bottombar

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.launchActivityForResult
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.*
import com.vn.vbeeon.presentation.adapter.deviceHome.DeviceLocalAdapter
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_home_page.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class HomePageFragment : BaseFragment() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: DeviceLocalAdapter
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        Timber.e("onResume")
        mainViewModel.loadDevices()
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home_page

    }

    override fun initView() {
        btn_add_device_empty_list.setOnSafeClickListener {
            Timber.e("click add device")
            activity?.launchActivityForResult<DeviceAddNewActivity>(1001 ) {
                putExtra(
                    ConstantCommon.KEY_SEND_OPTION_FRAGMENT, ConstantCommon.KEY_OPEN_FRAGMENT_DEVICE
                )
            }
        }
        mAdapter = DeviceLocalAdapter(itemClick = { it ->
            context?.launchActivity<SphygmomanometerActivity>()
        }, deleteClick = {
            mainViewModel.deleteDevice(it)
            Timber.d(""+it.categoryName)
        }, onClickDetail = {
            Timber.d(""+it.categoryName)
        })
        rvListDevice.apply { adapter = mAdapter }
        txt_link.setOnSafeClickListener {
            context?.launchActivity<FragmentActivity> {
                putExtra(
                    ConstantCommon.KEY_SEND_OPTION_FRAGMENT,
                    ConstantCommon.KEY_SEND_WEBVIEW_VBEEON_SP
                )
                putExtra(ConstantCommon.KEY_WEBVIEW_URL, "https://vbeeon.com")
            }
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
            MainViewModel::class.java
        )

    }

    override fun observable() {
        mainViewModel.devicesRes.observe(context as MainActivity, Observer {
            if (it.size > 0) {
                ll_listEmpty.visibility = View.GONE
                rvListDevice.visibility = View.VISIBLE
                mAdapter.setData(it)
                Timber.d("size ${it.size}")
            } else {
                rvListDevice.visibility = View.GONE
                ll_listEmpty.visibility = View.VISIBLE
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.e("onActivityResult")
        if (requestCode === 1001) {
            mainViewModel.loadDevices()
        }
    }

}
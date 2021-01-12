package com.vn.vbeeon.presentation.fragment.user

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
import com.vn.vbeeon.presentation.viewmodel.UserViewModel
import com.vsm.ambientmode.ui.timer.DeviceAddNewAdapter
import com.vsm.ambientmode.ui.timer.UserAdapter
import kotlinx.android.synthetic.main.fragment_add_new_device.*
import kotlinx.android.synthetic.main.fragment_add_new_device.txt_link
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class ListUserFragment : BaseFragment() {
    private lateinit var adapterUser: UserAdapter
    lateinit var mViewModel: UserViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_user
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "DANH SÁCH TÀI KHOẢN"
        adapterUser = UserAdapter { position, item ->

        }
        rvDeviceNew.apply { adapter = adapterUser }

    }

    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(activity as DeviceAddNewActivity, viewModelFactory).get(
            UserViewModel::class.java
        )
        mViewModel.getListUser()
    }

    override fun observable() {
        mViewModel.listUserRes.observe(this, Observer {
            adapterUser.setData(it)
        })
    }


}
package com.vn.vbeeon.presentation.activity

import android.os.Bundle
import com.vn.vbeeon.R
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.presentation.base.BaseActivity
import com.vn.vbeeon.presentation.fragment.convertDigital.ConvertDigitalDetailFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.FragmentVBeeOnMission
import com.vn.vbeeon.presentation.fragment.deviceAddNew.DeviceListNewFragment
import com.vn.vbeeon.presentation.fragment.user.AddUserFragment
import vn.neo.smsvietlott.common.di.util.ConstantCommon

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-December-2020
 * Time: 23:38
 * Version: 1.0
 */
class DeviceAddNewActivity : BaseActivity() {

    override fun provideLayoutId() = R.layout.activity_frame_layout

    override fun setupView(savedInstanceState: Bundle?) {
        val option = intent.getStringExtra(ConstantCommon.KEY_SEND_OPTION_FRAGMENT)
        when (option){
            ConstantCommon.KEY_OPEN_FRAGMENT_DEVICE -> openFragment(DeviceListNewFragment(), false)
            ConstantCommon.KEY_OPEN_FRAGMENT_USER -> openFragment(AddUserFragment(), false)
        }


    }
}
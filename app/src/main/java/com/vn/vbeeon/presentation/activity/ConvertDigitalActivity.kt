package com.vn.vbeeon.presentation.activity

import android.os.Bundle
import com.vn.vbeeon.R
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.presentation.base.BaseActivity
import com.vn.vbeeon.presentation.fragment.convertDigital.ConvertDigitalDetailFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.FragmentVBeeOnMission
import vn.neo.smsvietlott.common.di.util.ConstantCommon

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-December-2020
 * Time: 23:38
 * Version: 1.0
 */
class ConvertDigitalActivity : BaseActivity() {

    override fun provideLayoutId() = R.layout.activity_frame_layout

    override fun setupView(savedInstanceState: Bundle?) {
        val option = intent.getIntExtra(ConstantCommon.KEY_SEND_OPTION_CD, 0)
        when (option){
            ConstantCommon.KEY_SEND_CONVERT_DIGITAL_1 ->
                openFragment(ConvertDigitalDetailFragment(), false)
            ConstantCommon.KEY_SEND_CONVERT_DIGITAL_2 ->
                openFragment(FragmentVBeeOnMission(), false)
            ConstantCommon.KEY_SEND_CONVERT_DIGITAL_3 ->
                openFragment(FragmentVBeeOnMission(), false)

        }
       // openFragment(ConvertDigitalDetailFragment(), false)

    }
}
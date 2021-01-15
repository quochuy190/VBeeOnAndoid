package com.vn.vbeeon.presentation.activity

import android.os.Bundle
import com.vn.vbeeon.R
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.presentation.base.BaseActivity
import com.vn.vbeeon.presentation.fragment.convertDigital.ConvertDigitalDetailFragment
import com.vn.vbeeon.presentation.fragment.convertDigital.FragmentVBeeOnMission
import com.vn.vbeeon.presentation.fragment.convertDigital.FragmentVbeeonITForLife
import com.vn.vbeeon.presentation.fragment.convertDigital.WebViewFragment
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 25-December-2020
 * Time: 23:38
 * Version: 1.0
 */
class FragmentActivity : BaseActivity() {

    override fun provideLayoutId() = R.layout.activity_frame_layout

    override fun setupView(savedInstanceState: Bundle?) {
        val option = intent.getIntExtra(ConstantCommon.KEY_SEND_OPTION_FRAGMENT, 0)
        val url = intent.getStringExtra(ConstantCommon.KEY_WEBVIEW_URL)
        Timber.d("ConvertDigitalActivity "+option)
        when (option){
            ConstantCommon.KEY_SEND_CONVERT_DIGITAL_1 ->
                openFragment(ConvertDigitalDetailFragment(), false)
            ConstantCommon.KEY_SEND_CONVERT_DIGITAL_2 ->
                openFragment(FragmentVbeeonITForLife(), false)
            ConstantCommon.KEY_SEND_CONVERT_DIGITAL_3 ->
                openFragment(FragmentVBeeOnMission(), false)
            ConstantCommon.KEY_SEND_CONVERT_DIGITAL_4 ->
                openFragment(
                    WebViewFragment.newInstance(
                        "https://vbeeon.com", ConstantCommon.KEY_WEBVIEW_URL
                    ), false)
            ConstantCommon.KEY_SEND_WEBVIEW_VBEEON_SP ->{
                if (url!=null&&url.length>0){
                    openFragment(
                        WebViewFragment.newInstance(
                            url, ConstantCommon.KEY_WEBVIEW_URL
                        ), false)
                }else{
                    openFragment(
                        WebViewFragment.newInstance(
                            "https://vbeeon.com/san-pham", ConstantCommon.KEY_WEBVIEW_URL
                        ), false)
                }
            }


        }
       // openFragment(ConvertDigitalDetailFragment(), false)

    }
}
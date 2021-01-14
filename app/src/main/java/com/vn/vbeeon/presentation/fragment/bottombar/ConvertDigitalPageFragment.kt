package com.vn.vbeeon.presentation.fragment.bottombar

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.FragmentActivity

import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_convert_digital.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class ConvertDigitalPageFragment : BaseFragment() {
    lateinit var mainViewModel: MainViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_convert_digital
    }

    override fun initView() {
        itemBaseDigital.setOnClickListener {
            startActivityOption(0)
        }
        itemSmartDigital.setOnClickListener {
            startActivityOption(1)
        }
        itemMissionDigital.setOnClickListener {
            startActivityOption(2)
        }

    }

    private fun startActivityOption(option : Int) {
        context?.launchActivity<FragmentActivity>{
            putExtra(ConstantCommon.KEY_SEND_OPTION_FRAGMENT, option)
        }
    }

    override fun initViewModel() {
//        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
//            MainViewModel::class.java)
    }

    override fun observable() {

    }


}
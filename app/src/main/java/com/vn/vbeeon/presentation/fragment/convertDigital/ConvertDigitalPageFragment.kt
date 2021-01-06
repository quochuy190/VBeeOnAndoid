package com.vn.vbeeon.presentation.fragment.convertDigital

import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import com.vn.vbeeon.utils.AppUtils.readFile
import kotlinx.android.synthetic.main.fragment_convert_digital.*
import kotlinx.android.synthetic.main.toolbar_main.*
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
        context?.launchActivity<ConvertDigitalActivity>{
            putExtra(ConstantCommon.KEY_SEND_OPTION_CD, option)
        }
    }

    override fun initViewModel() {
//        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
//            MainViewModel::class.java)
    }

    override fun observable() {

    }




}
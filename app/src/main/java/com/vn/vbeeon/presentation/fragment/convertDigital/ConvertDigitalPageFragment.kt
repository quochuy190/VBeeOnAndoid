package com.vn.vbeeon.presentation.fragment.convertDigital

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import com.vn.vbeeon.utils.AppUtils.readFile
import kotlinx.android.synthetic.main.fragment_convert_digital.*



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
        val jsonString = context?.assets?.readFile("ChuyenDoiSo.json")
        var gson = Gson()
        //var mMineUserEntity = gson?.fromJson(jsonString, ObjHtmlData::class.java)
        val objectList = gson.fromJson(jsonString, Array<ObjHtmlData>::class.java).asList()
        itemBaseDigital.setOnClickListener {
            context?.launchActivity<ConvertDigitalActivity>()
        }
    }

    override fun initViewModel() {
//        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
//            MainViewModel::class.java)
    }

    override fun observable() {

    }




}
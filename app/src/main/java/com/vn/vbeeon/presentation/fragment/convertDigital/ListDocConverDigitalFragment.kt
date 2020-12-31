package com.vn.vbeeon.presentation.fragment.convertDigital

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.adapter.MainViewPagerAdapter
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.fragment.HomePageFragment
import com.vn.vbeeon.presentation.viewmodel.ConvertDigitalViewModel
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_convert_digital.*
import kotlinx.android.synthetic.main.fragment_convert_digital_detail.*
import kotlinx.android.synthetic.main.fragment_main.*
import vn.neo.smsvietlott.common.utils.AppUtils.readFile


@Suppress("DEPRECATION")
class ListDocConverDigitalFragment : BaseFragment() {
    lateinit var mViewModel: FragmentListWebHtmlViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_doc_convert_digital
    }

    override fun initView() {

//        val jsonString = context?.assets?.readFile("ChuyenDoiSo.json")
//        Log.d("TAG", "initView: "+jsonString)
//        var gson = Gson()
//        //var mMineUserEntity = gson?.fromJson(jsonString, ObjHtmlData::class.java)
//        val objectList = gson.fromJson(jsonString, Array<ObjHtmlData>::class.java).asList()
//        Log.d("TAG", "initView: "+objectList.size)

    }

    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(activity as ConvertDigitalActivity, viewModelFactory).get(
            FragmentListWebHtmlViewModel::class.java)
        context?.let { mViewModel.getListHtml(it, "ChuyenDoiSo.json") }
    }

    override fun observable() {

    }
}
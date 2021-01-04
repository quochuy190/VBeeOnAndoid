package com.vn.vbeeon.presentation.fragment.convertDigital

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.domain.model.ObjHtmlData
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.adapter.MainViewPagerAdapter
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.fragment.HomePageFragment
import com.vn.vbeeon.presentation.viewmodel.ConvertDigitalViewModel
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import com.vn.vbeeon.utils.AppUtils.readFile
import kotlinx.android.synthetic.main.fragment_convert_digital.*
import kotlinx.android.synthetic.main.fragment_convert_digital_detail.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class ConvertDigitalDetailFragment : BaseFragment() {

    lateinit var mainViewModel: ConvertDigitalViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_convert_digital_detail
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        initViewPager()
        val jsonString = context?.assets?.readFile("ChuyenDoiSo.json")
        var gson = Gson()
        //var mMineUserEntity = gson?.fromJson(jsonString, ObjHtmlData::class.java)
        val objectList = gson.fromJson(jsonString, Array<ObjHtmlData>::class.java).asList()
    }

    override fun initViewModel() {
//        mainViewModel = ViewModelProviders.of(activity as ConvertDigitalActivity, viewModelFactory).get(
//            ConvertDigitalViewModel::class.java)
    }

    override fun observable() {

    }

    private fun initViewPager() {
        val adapter = MainViewPagerAdapter(childFragmentManager)
        adapter.addFragment(ListDocConverDigitalFragment.newInstance("ChuyenDoiSo.json"), getString(R.string.tab_base_c_digital))
        adapter.addFragment(ListDocConverDigitalFragment.newInstance("ChuyenDoiSoChoDoanhNghiep.json"), getString(R.string.tab_citizen_c_digital))
        adapter.addFragment(ListDocConverDigitalFragment.newInstance("ChuyenDoiSoChoNguoiDan.json"), getString(R.string.tab_enterprise_c_digital))
        adapter.addFragment(ListDocConverDigitalFragment.newInstance("ChuyenDoiSoChoNhaNuoc.json"), getString(R.string.tab_government_c_digital))

        vpConvertDigital.adapter = adapter
        vpConvertDigital.setOffscreenPageLimit(4)
        vpConvertDigital.setPageScrollEnabled(true)
        tlConvertDigital.setupWithViewPager(vpConvertDigital)
    }


}
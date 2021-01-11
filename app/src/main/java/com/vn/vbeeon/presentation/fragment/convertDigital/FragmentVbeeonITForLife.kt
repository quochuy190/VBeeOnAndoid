package com.vn.vbeeon.presentation.fragment.convertDigital

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.common.extensions.setTextHTML
import com.vn.vbeeon.presentation.activity.FragmentActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_it_for_life.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class FragmentVbeeonITForLife : BaseFragment() {

   // lateinit var mainViewModel: ConvertDigitalViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_it_for_life
    }

    override fun initView() {
        Timber.e("FragmentVbeeonITForLife")
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "GIỚI THIỆU VỀ VBEEON"
        tvWebsite.setOnSafeClickListener {
            (context as FragmentActivity).openFragment(WebViewFragment.newInstance("https://vbeeon.com/gioi-thieu",
                ConstantCommon.KEY_WEBVIEW_URL), true)
        }
        val txtMission = "<font color='#FFB900'>VEBEEON</font> ĐƯỢC XÂY DỰNG BỞI MỘT NHÓM CÁC CÔNG TY CÔNG NGHỆ CAO CỦA " +
                "<font color='#FFB900'>VIỆT NAM</font>, CHÚNG TÔI NGHIÊN CỨU VÀ SỬ DỤNG CÁC CÔNG NGHỆ HIỆN ĐẠI NHẤT " +
                "<font color='#00FFFF'>KẾT NỐI MỌI VẬT LÊN INTERNET</font> ĐỂ CUNG CẤP DỊCH VỤ SỐ VÀO MỌI NGÕ NGÁCH CỦA CUỘC SỐNG" +
                " VỚI ƯỚC VỌNG MỘT <font color='#FFB900'>VIỆT NAM</font> SỐ HÓA"
        tvMission.text  = setTextHTML(txtMission)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvMission.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        val txtCompany = "<font color='#3497FD'>Bee Innovative</font> và <font color='#3497FD'>AHG</font>"
        tvCompany.text  = setTextHTML(txtCompany)

    }

    override fun initViewModel() {
//        mainViewModel = ViewModelProviders.of(activity as ConvertDigitalActivity, viewModelFactory).get(
//            ConvertDigitalViewModel::class.java)
    }

    override fun observable() {

    }


}
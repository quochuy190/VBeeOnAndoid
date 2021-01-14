package com.vn.vbeeon.presentation.fragment.introduce

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.IntroduceActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import com.vn.vbeeon.utils.SharedPrefs
import kotlinx.android.synthetic.main.fragment_introduce.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon


@Suppress("DEPRECATION")
class IntroduceFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_introduce
    }

    override fun initView() {
        btnIntroduce.setOnSafeClickListener {
            (context as IntroduceActivity).openFragment(IntroduceInfoFragment(), true)
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as IntroduceActivity, viewModelFactory).get(
            MainViewModel::class.java)
    }

    override fun observable() {

    }


}
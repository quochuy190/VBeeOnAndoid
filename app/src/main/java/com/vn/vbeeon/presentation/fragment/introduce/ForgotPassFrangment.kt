package com.vn.vbeeon.presentation.fragment.introduce

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.presentation.activity.IntroduceActivity
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel


@Suppress("DEPRECATION")
class ForgotPassFrangment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_fogot_pass
    }

    override fun initView() {
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as IntroduceActivity, viewModelFactory).get(
            MainViewModel::class.java)
    }

    override fun observable() {

    }


}
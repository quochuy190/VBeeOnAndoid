package com.vn.vbeeon.presentation.fragment.user

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.domain.model.User
import com.vn.vbeeon.presentation.activity.FragmentActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class AddUserFragment : BaseFragment() {
    lateinit var mViewModel: UserViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_add_user
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "ĐĂNG KÝ"
        btnRegister.setOnSafeClickListener { mViewModel.saveUser(User(0, edt_name.text.toString(), 0, "10/01/90")) }
    }

    override fun initViewModel() {
        mViewModel = ViewModelProviders.of(activity as FragmentActivity, viewModelFactory).get(
            UserViewModel::class.java)

    }

    override fun observable() {

    }


}
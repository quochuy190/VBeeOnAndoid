package com.vn.vbeeon.presentation.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.vn.vbeeon.MainActivity
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel


class MainFragment : BaseFragment() {

    lateinit var mainViewModel: MainViewModel

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initView() {

    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
            MainViewModel::class.java)
    }

    override fun observable() {

    }


}
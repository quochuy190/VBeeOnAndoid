package com.vn.vbeeon.presentation.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.presentation.activity.MainActivity
import com.vn.vbeeon.presentation.adapter.MainViewPagerAdapter
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*


@Suppress("DEPRECATION")
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
        return R.layout.fragment_main
    }

    override fun initView() {
        initViewPager();
        bnv.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.it_home -> {
                    vp_main.currentItem =0
                }
                R.id.it_monitoring -> {
                    vp_main.currentItem =1
                }
                R.id.it_online -> {
                    vp_main.currentItem =2
                }
                R.id.it_control -> {
                    vp_main.currentItem =3
                }
                R.id.it_chuyendoiso -> {
                    vp_main.currentItem =4
                }
            }
            true
        }
    }

    private fun initViewPager() {
        val adapter = MainViewPagerAdapter(childFragmentManager)

        adapter.addFragment(DemoFragment(), "")
        adapter.addFragment(DemoFragment(), "")
        adapter.addFragment(DemoFragment(), "")
        adapter.addFragment(DemoFragment(), "")
        adapter.addFragment(DemoFragment(), "")

        vp_main.adapter = adapter
        vp_main.setOffscreenPageLimit(5)
        vp_main.setPageScrollEnabled(false)
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProviders.of(activity as MainActivity, viewModelFactory).get(
            MainViewModel::class.java)
    }

    override fun observable() {

    }


}
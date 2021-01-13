package com.vn.vbeeon.presentation.fragment.convertDigital

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.openFragment
import com.vn.vbeeon.presentation.activity.FragmentActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.FragmentListWebHtmlViewModel
import com.vsm.ambientmode.ui.timer.HtmlAdapter
import kotlinx.android.synthetic.main.fragment_list_doc_convert_digital.*
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConstantCommon

@Suppress("DEPRECATION")
class ListDocConverDigitalFragment : BaseFragment() {
    lateinit var mViewModel: FragmentListWebHtmlViewModel
    private lateinit var mHtmlAdapter: HtmlAdapter
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    companion object {
        fun newInstance(jsonFile: String): ListDocConverDigitalFragment {
            val fragment = ListDocConverDigitalFragment()
            val args = Bundle()
            args.putString("jsonFile", jsonFile)
            fragment.setArguments(args)
            return fragment
        }
    }

    var jsonFile: String = "";
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("jsonFile")?.let {
            jsonFile = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_doc_convert_digital
    }

    override fun initView() {
        mHtmlAdapter = HtmlAdapter { _, item ->
            (context as FragmentActivity).openFragment(WebViewFragment.newInstance(item.content, ConstantCommon.KEY_WEBVIEW_HTML), true)
        }
        rvListHtml.apply { adapter = mHtmlAdapter }

    }

    override fun initViewModel() {
        mViewModel =
            ViewModelProviders.of(activity as FragmentActivity, viewModelFactory).get(
                FragmentListWebHtmlViewModel::class.java
            )
        Timber.d("file  $jsonFile")
        context?.let {
            when (jsonFile) {
                ConstantCommon.JSON_HTML_BASE -> mViewModel.getListHtmlBase(it, jsonFile)
                ConstantCommon.JSON_HTML_CITIZEN -> mViewModel.getListHtmlCitizen(it, jsonFile)
                ConstantCommon.JSON_HTML_Enterprise -> mViewModel.getListHtmlEnterprise(
                    it,
                    jsonFile
                )
                ConstantCommon.JSON_HTML_Government -> mViewModel.getListHtmlGovernment(
                    it,
                    jsonFile
                )
                else -> { // Note the block
                    print("not json File")
                }
            }

        }
    }

    override fun observable() {
        when (jsonFile) {
            ConstantCommon.JSON_HTML_BASE -> mViewModel.listHtmlBase.observe(this, Observer {
                Timber.d("onItemClick ${it.size}")
                mHtmlAdapter.setData(it)
            })
            ConstantCommon.JSON_HTML_CITIZEN -> mViewModel.listHtmlCity.observe(this, Observer {
                Timber.d("onItemClick ${it.size}")
                mHtmlAdapter.setData(it)
            })
            ConstantCommon.JSON_HTML_Enterprise -> mViewModel.listHtmlEnterp.observe(this, Observer {
                Timber.d("onItemClick ${it.size}")
                mHtmlAdapter.setData(it)
            })
            ConstantCommon.JSON_HTML_Government -> mViewModel.listHtmlGove.observe(this, Observer {
                Timber.d("onItemClick ${it.size}")
                mHtmlAdapter.setData(it)
            })
            else -> { // Note the block
                print("not json File")
            }
        }

    }
}
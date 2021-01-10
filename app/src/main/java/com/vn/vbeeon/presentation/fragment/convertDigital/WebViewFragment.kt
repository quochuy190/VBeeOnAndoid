package com.vn.vbeeon.presentation.fragment.convertDigital

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.presentation.activity.ConvertDigitalActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.FragmentListWebHtmlViewModel
import kotlinx.android.synthetic.main.fragment_webv.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber

@Suppress("DEPRECATION")
class WebViewFragment : BaseFragment() {
    lateinit var mViewModel: FragmentListWebHtmlViewModel
    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    companion object {
        fun newInstance(jsonFile: String): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString("content", jsonFile)
            fragment.setArguments(args)
            return fragment
        }
    }

    var content: String = "";
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString("content")?.let {
            content = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_webv
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "Chuyển đổi số"
        wvConvertDigital.getSettings().setJavaScriptEnabled(true);
        wvConvertDigital.setBackgroundColor(Color.TRANSPARENT);
        if (content.equals("vbeeon.com")){
            tv_toolbar_title.text = "vbeeon"
            tvSource.visibility = View.GONE
            wvConvertDigital.loadUrl("https://vbeeon.com")
        }else  if (content.equals("vbeeon.com/gioi-thieu")){
            tv_toolbar_title.text = "Về chúng tôi"
            tvSource.visibility = View.GONE
            wvConvertDigital.loadUrl("https://vbeeon.com/gioi-thieu")
        }else  if (content.equals("vbeeon.com/san-pham")){
            tv_toolbar_title.text = "Sản phẩm"
            tvSource.visibility = View.GONE
            wvConvertDigital.loadUrl("https://vbeeon.com")
        }else{
            content="<font color='white'>" + content + "</font>";
            tvSource.visibility = View.VISIBLE
            wvConvertDigital.loadData(content, "text/html; charset=utf-8", "UTF-8");
        }
    }

    override fun initViewModel() {
        mViewModel =
            ViewModelProviders.of(activity as ConvertDigitalActivity, viewModelFactory).get(
                FragmentListWebHtmlViewModel::class.java
            )

    }

    override fun observable() {

    }
}
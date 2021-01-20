package com.vn.vbeeon.presentation.fragment.user

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.vn.vbeeon.R
import com.vn.vbeeon.common.di.component.AppComponent
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.domain.model.User
import com.vn.vbeeon.presentation.activity.SphygmomanometerActivity
import com.vn.vbeeon.presentation.base.BaseFragment
import com.vn.vbeeon.presentation.viewmodel.UserViewModel
import com.vn.vbeeon.utils.AppUtils
import com.vn.vbeeon.utils.AppUtils.getAgefromBirthday
import kotlinx.android.synthetic.main.fragment_add_user.btnRegister
import kotlinx.android.synthetic.main.fragment_register_user.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber
import java.util.*


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
        return R.layout.fragment_register_user
    }

    override fun initView() {
        edtBirthday.setOnSafeClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val dpd =
                activity?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            // Display Selected date in textbox
                            edtBirthday.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)

                        },
                        year,
                        month,
                        day
                    )
                }

            dpd?.show()
        }
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "ĐĂNG KÝ"
        btnRegister.setOnSafeClickListener {
            var sex: Int = 0
            if (male.isChecked) {
                sex = 0
            } else if (famale.isChecked)
                sex = 1
            if (validateEdt()){
                mViewModel.saveUser(User(0, edtFullName.text.toString(), 0, edtBirthday.text.toString(), sex,
                    getAgefromBirthday(edtBirthday.text.toString())))
                showMessage(R.string.register_success)
                activity?.onBackPressed()
            }

        }
    }

    override fun initViewModel() {
        mViewModel =
            ViewModelProviders.of(activity as SphygmomanometerActivity, viewModelFactory).get(
                UserViewModel::class.java
            )

    }

    override fun observable() {

    }

    private fun validateEdt(): Boolean {
        if (edtFullName.text.toString().length == 0) {
            edtFullName.requestFocus()
            edtFullName.setSelection(0)
            showMessage(R.string.emtry_input_name)
            return false
        }
        if (edtBirthday.text.toString().length == 0) {
            edtBirthday.requestFocus()
            edtBirthday.setSelection(0)
            showMessage(R.string.emtry_input_birthday)
            return false
        }
        if (!AppUtils.checkCurrentDateTime(edtBirthday.text.toString())){
            showMessage(R.string.input_birthday_error)
            return false
        }
        return true
    }

}
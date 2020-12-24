package com.vn.vbeeon.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(initFragment())
    }

    fun replaceFragment(fragment: BaseFragment, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(getIdContainer(), fragment)
        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }

        transaction.commit()
    }

    fun addFragment(fragment: BaseFragment, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .add(getIdContainer(), fragment)

        if (addToBackStack) {
            transaction.addToBackStack(fragment.javaClass.simpleName)
        }

        transaction.commit()
    }

    abstract fun initFragment(): BaseFragment

    abstract fun getIdContainer(): Int
}
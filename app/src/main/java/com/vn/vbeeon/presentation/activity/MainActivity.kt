package com.vn.vbeeon.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.vn.vbeeon.R
import com.vn.vbeeon.common.extensions.launchActivity
import com.vn.vbeeon.common.extensions.setOnSafeClickListener
import com.vn.vbeeon.utils.AppUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import vn.neo.smsvietlott.common.di.util.ConstantCommon

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val tvVersion: TextView = findViewById(R.id.tvVersion)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        imgAdd.setOnSafeClickListener {
            launchActivity<DeviceAddNewActivity>()
        }
        navView.setNavigationItemSelectedListener(this)
        tvVersion.text = "Version: "+AppUtils.versionName(this)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    fun setTitleMain(title: String){
        tvTitleMain.text = title

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_homepage_new -> {

            }
            R.id.nav_shop -> {
                launchActivity<ConvertDigitalActivity>{
                    putExtra(ConstantCommon.KEY_SEND_OPTION_CD, ConstantCommon.KEY_SEND_WEBVIEW_VBEEON_SP)
                }
            }
            R.id.nav_intro -> {
                launchActivity<ConvertDigitalActivity>{
                    putExtra(ConstantCommon.KEY_SEND_OPTION_CD, ConstantCommon.KEY_SEND_CONVERT_DIGITAL_4)
                }
            }
            R.id.nav_account -> {
//                launchActivity<ConvertDigitalActivity>{
//                    putExtra(ConstantCommon.KEY_SEND_OPTION_CD, ConstantCommon.KEY_SEND_CONVERT_DIGITAL_4)
//                }
            }
            R.id.nav_share -> {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"Tải VBeeOn để được trải nghiệm các công nghệ mới nhất")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
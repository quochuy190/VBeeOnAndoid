package com.vn.vbeeon.presentation.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.vn.vbeeon.utils.SharedPrefs
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
        val headerView: View = navView.getHeaderView(0)
        val navController = findNavController(R.id.nav_host_fragment)
        val tvFullNameUser: TextView = headerView.findViewById(R.id.tvFullNameUser)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        imgAdd.setOnSafeClickListener {
            launchActivity<DeviceAddNewActivity>{
                putExtra(ConstantCommon.KEY_SEND_OPTION_FRAGMENT, ConstantCommon.KEY_OPEN_FRAGMENT_DEVICE)
            }
        }
        navView.setNavigationItemSelectedListener(this)
        tvVersion.text = "Version: "+AppUtils.versionName(this)

        tvFullNameUser.text = SharedPrefs.instance.get(ConstantCommon.USER_FULL_NAME, String::class.java)
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
                launchActivity<FragmentActivity>{
                    putExtra(ConstantCommon.KEY_SEND_OPTION_FRAGMENT, ConstantCommon.KEY_SEND_WEBVIEW_VBEEON_SP)
                    putExtra(ConstantCommon.KEY_WEBVIEW_URL, "https://vbeeon.com")
                }
            }
            R.id.nav_intro -> {
                launchActivity<FragmentActivity>{
                    putExtra(ConstantCommon.KEY_SEND_OPTION_FRAGMENT, 1)
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
    fun checkPermission() {
        if (this.let {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE)
            }
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            callPhone()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    fun callPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0823830506"))
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}
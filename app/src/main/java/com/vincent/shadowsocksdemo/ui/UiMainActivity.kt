package com.vincent.shadowsocksdemo.ui

import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.bases.BaseFragmentActivity
import com.vincent.shadowsocksdemo.databinding.ActivityMainBinding
import com.vincent.shadowsocksdemo.model.CommonConstants

class UiMainActivity : BaseFragmentActivity<ActivityMainBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getToolbar(): Toolbar? {
        return mBinding.includeHome.toolbar
    }

    override fun getLoadingCircle(): ProgressBar? {
        return mBinding.includeHome.toolbarLoadingCircle
    }

    override fun getDrawer(): DrawerLayout? {
        return mBinding.drawerLayout
    }

    override fun getNavigationView(): NavigationView? {
        return mBinding.navView
    }

    override fun init() {
        super.init()

    }

    override fun onBackToHome() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            CommonConstants.TOOLBAR_HOME -> super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // TODO Navigation Menu Item Click Events
        return true
    }

    override fun onBackPressed() {
        getDrawer()?.run {
            if (isDrawerOpen(GravityCompat.START)) {
                closeDrawer(GravityCompat.START)
                return
            }
        }

        super.onBackPressed()
    }
}

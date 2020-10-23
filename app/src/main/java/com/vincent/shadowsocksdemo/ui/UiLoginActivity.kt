package com.vincent.shadowsocksdemo.ui

import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.databinding.ActivityLoginBinding
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.ui.bases.BaseFragmentActivity
import com.vincent.shadowsocksdemo.ui.fragments.login.UiLoginMainFragment

/**
 * Created by Vincent on 2020/1/31.
 */
class UiLoginActivity : BaseFragmentActivity<ActivityLoginBinding>() {

    override fun onBackToFirstFragment() {}

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun getToolbar(): Toolbar? = null

    override fun getLoadingCircle(): ProgressBar? = null

    override fun getDrawer(): DrawerLayout? = null

    override fun getNavigationView(): NavigationView? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean = false

    override fun onClick(v: View?) {}

    override fun onInitDone() {
        openLoginMainFragment()
    }

    private fun openLoginMainFragment() {
        goToFragment(UiLoginMainFragment(), true, Const.BACK_LOGIN)
    }
}
package com.vincent.shadowsocksdemo.ui

import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.databinding.ActivityMainBinding
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.ui.bases.BaseFragmentActivity
import com.vincent.shadowsocksdemo.ui.fragments.UiHomeFragment

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

    override fun onInitDone() {
        inflateHomeFragment()

        encryptTest()
    }

    private fun encryptTest() {
//        LogUtil.i(TAG, "TestAESDecrypt: ${EncryptUtils.decryptBase64EncodedData("vzkF972yc0dtXtkt5OVumKy1JNOK0ga7p0CmhlwyUl9cMAhPKAQUgv/1Qje5MgdriKkJqkXQhgybyI55Q4I=", "12345678901234567890123456789012")}")
//        val encoded = EncryptUtils.encryptAndBase64Encode("Hello World!", "1234567812345678")
//        LogUtil.i(TAG, "Encode: $encoded")
//        LogUtil.i(TAG, "Decode: ${EncryptUtils.decryptBase64EncodedData(encoded, "1234567812345678")}")
    }

    private fun inflateHomeFragment() {
        goToFragment(UiHomeFragment(), true, null)
    }

    override fun onBackToFirstFragment() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            Const.TOOLBAR_HOME -> super.onBackPressed()
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

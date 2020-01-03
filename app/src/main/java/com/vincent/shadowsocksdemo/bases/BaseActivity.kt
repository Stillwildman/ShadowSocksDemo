package com.vincent.shadowsocksdemo.bases

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.utilities.Utility

/**
 * Created by Vincent on 2020/1/3.
 */
abstract class BaseActivity<bindingView : ViewDataBinding> : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    protected val TAG = javaClass.simpleName

    protected abstract fun getLayoutId() : Int
    protected abstract fun getToolbar() : Toolbar?
    protected abstract fun getLoadingCircle() : ProgressBar?
    protected abstract fun getDrawer() : DrawerLayout?
    protected abstract fun getNavigationView() : NavigationView?
    protected abstract fun init()

    protected var toggle : ActionBarDrawerToggle? = null

    protected val mBinding : bindingView by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(this), getLayoutId(), null, false) as bindingView
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mBinding.root)

        initToolbar()
        initDrawer()
        initNavigationView()

        init()
    }

    private fun initToolbar() {
        getToolbar()?.apply {
            setSupportActionBar(this)
            supportActionBar?.run {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    private fun initDrawer() {
        getDrawer()?.apply {
            toggle = ActionBarDrawerToggle(this@BaseActivity, this, getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close).also {
                it.syncState()
                it.toolbarNavigationClickListener = this@BaseActivity
            }
        }
    }

    private fun initNavigationView() {
        getNavigationView()?.let {
            it.setNavigationItemSelectedListener(this)
            it.layoutParams.width = (Utility.getScreenWidth() * 0.7).toInt()
            it.getHeaderView(0).layoutParams.height = (Utility.getScreenHeight() * 0.3).toInt()
        }
    }

    protected fun showLoadingCircle(isShow : Boolean) = getLoadingCircle()?.run { visibility = if (isShow) View.VISIBLE else View.GONE }

    override fun onBackPressed() {
        getDrawer()?.run {
            if (isDrawerOpen(GravityCompat.START)) {
                closeDrawer(GravityCompat.START)
                return
            }
        }

        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart!!!")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume!!!")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause!!!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop!!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy!!!")
    }
}
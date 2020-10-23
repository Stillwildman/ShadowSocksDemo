package com.vincent.shadowsocksdemo

import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.github.shadowsocks.Core
import com.vincent.shadowsocksdemo.ui.UiMainActivity
import com.vincent.shadowsocksdemo.utilities.LogUtil
import org.greenrobot.eventbus.EventBus

/**
 * Created by Vincent on 2020/1/2.
 */
class AppController : MultiDexApplication() {

    private val tag = "AppController"

    companion object {
        @get:Synchronized
        lateinit var instance : AppController
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        Core.init(this, UiMainActivity::class)

        EventBus.builder().addIndex(MyEventBusIndex()).installDefaultEventBus()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Core.updateNotificationChannels()
    }

    fun hideKeyboardByGivenView(isHide: Boolean, view: View) {
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (isHide) imm.hideSoftInputFromWindow(
            view.applicationWindowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        else {
            view.requestFocus()
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun detectLayoutDimensions() {
        val option = BitmapFactory.Options().apply { inJustDecodeBounds = true }

        BitmapFactory.decodeResource(resources, R.drawable.ic_background_map, option)

        LogUtil.i(tag, "LayoutBackgroundDimensions: Width: ${option.outWidth} Height: ${option.outHeight}")
    }
}
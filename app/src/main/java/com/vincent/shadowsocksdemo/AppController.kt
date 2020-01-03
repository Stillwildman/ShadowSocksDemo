package com.vincent.shadowsocksdemo

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.github.shadowsocks.Core
import com.vincent.shadowsocksdemo.ui.UiMainActivity

/**
 * Created by Vincent on 2020/1/2.
 */
class AppController : MultiDexApplication() {

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
        Core.init(this, UiMainActivity::class)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Core.updateNotificationChannels()
    }
}
package com.vincent.shadowsocksdemo.utilities

import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.StringRes
import com.vincent.shadowsocksdemo.AppController

/**
 * Created by Vincent on 2020/1/3.
 */
object Utility {

    fun toastShort(msg: String) {
        Toast.makeText(AppController.instance.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastShort(@StringRes msgResId: Int) {
        toastShort(AppController.instance.applicationContext.getString(msgResId))
    }

    fun toastLong(msg: String) {
        Toast.makeText(AppController.instance.applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    fun toastLong(@StringRes msgResId: Int) {
        toastLong(AppController.instance.applicationContext.getString(msgResId))
    }

    fun getScreenWidth(): Int {
        val dm: DisplayMetrics = AppController.instance.applicationContext.resources.displayMetrics
        return dm.widthPixels
    }

    fun getScreenHeight(): Int {
        val dm: DisplayMetrics = AppController.instance.applicationContext.resources.displayMetrics
        return dm.heightPixels
    }
}
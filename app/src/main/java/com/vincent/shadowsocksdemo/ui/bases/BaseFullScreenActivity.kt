package com.vincent.shadowsocksdemo.ui.bases

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vincent.shadowsocksdemo.R

/**
 * Created by Vincent on 2020/1/14.
 */
abstract class BaseFullScreenActivity: AppCompatActivity() {

    protected val TAG = javaClass.simpleName

    protected abstract fun getLayoutId(): Int
    protected abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(R.style.AppFullScreenTheme)
        setFullScreenFlags()
        setContentView(getLayoutId())
        init()
    }

    private fun setFullScreenFlags() {
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        window.decorView.systemUiVisibility = uiOptions
    }
}
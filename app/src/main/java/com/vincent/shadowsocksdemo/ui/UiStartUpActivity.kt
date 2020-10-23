package com.vincent.shadowsocksdemo.ui

import android.content.Intent
import android.os.Handler
import com.vincent.shadowsocksdemo.AppController
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.ui.bases.BaseFullScreenActivity

/**
 * Created by Vincent on 2020/1/14.
 */
class UiStartUpActivity: BaseFullScreenActivity() {

    override fun getLayoutId(): Int = R.layout.activity_start_up

    override fun init() {
        AppController.instance.detectLayoutDimensions()

        // TODO Initial check.

        //goToLoginActivity()
        goToMainActivity()
    }

    private fun goToLoginActivity() {
        Handler().postDelayed( {
            this.startActivity(Intent(this, UiLoginActivity::class.java))
            this.finish()
        }, 1500 )
    }

    private fun goToMainActivity() {
        Handler().postDelayed( {
            this.startActivity(Intent(this, UiMainActivity::class.java))
            this.finish()
        }, 1500 )
    }
}
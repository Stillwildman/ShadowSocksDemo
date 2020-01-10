package com.vincent.shadowsocksdemo.ui.fragments

import com.vincent.shadowsocksdemo.AppController.Companion.instance
import com.vincent.shadowsocksdemo.callbacks.OnSelectionDoneCallback
import com.vincent.shadowsocksdemo.utilities.DialogHelper.showSimpleEditDialog

/**
 * Created by Vincent on 2020/1/10.
 */
class Test {
    private fun test() {
        showSimpleEditDialog(
            instance.applicationContext,
            object : OnSelectionDoneCallback {
                override fun onSelectionDone(name: String) {}
            })
    }
}
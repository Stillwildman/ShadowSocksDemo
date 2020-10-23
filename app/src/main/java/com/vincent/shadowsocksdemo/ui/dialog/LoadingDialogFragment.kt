package com.vincent.shadowsocksdemo.ui.dialog

import android.view.Window
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.databinding.DialogLoadingCircleBinding
import com.vincent.shadowsocksdemo.ui.bases.BaseDialogFragment

/**
 * Created by Vincent on 2020/2/4.
 */
class LoadingDialogFragment : BaseDialogFragment<DialogLoadingCircleBinding>() {

    override fun getLayoutId(): Int = R.layout.dialog_loading_circle

    override fun setDialogWindowAttrs(window: Window) {

    }

    override fun init() {

    }
}
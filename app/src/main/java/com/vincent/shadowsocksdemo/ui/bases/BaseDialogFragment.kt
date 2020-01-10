package com.vincent.shadowsocksdemo.ui.bases

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.vincent.shadowsocksdemo.AppController
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.model.Const

/**
 * Created by Vincent on 2020/1/10.
 */
abstract class BaseDialogFragment<bindingView: ViewDataBinding>: DialogFragment(), Const {

    protected val TAG = javaClass.simpleName

    protected abstract fun getLayoutId(): Int
    protected abstract fun init()

    protected lateinit var mBinding: bindingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog!!!")

        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        return dialog
    }

    override fun onStart() {
        super.onStart()
        setDialogSize()
    }

    private fun setDialogSize() {
        dialog?.window?.apply {
            //val width = (Utility.getScreenWidth() * 0.9).toInt()
            //val height = (Utility.getScreenHeight() * 0.8).toInt()

            setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            val background = ContextCompat.getDrawable(AppController.instance.applicationContext, R.drawable.background_white_corner_10dp)
            setBackgroundDrawable(background)

            attributes.run {
                dimAmount = 0.5f
                flags = flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            }
            attributes = attributes
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume!!!")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        init()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundColor(Color.WHITE)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause!!!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop!!!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView!!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy!!!")
    }
}
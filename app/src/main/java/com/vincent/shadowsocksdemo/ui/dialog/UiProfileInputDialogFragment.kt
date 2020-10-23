package com.vincent.shadowsocksdemo.ui.dialog

import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.vincent.shadowsocksdemo.AppController
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.callbacks.OnValueSetCallback
import com.vincent.shadowsocksdemo.databinding.DialogTextInputBinding
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.ui.bases.BaseDialogFragment

/**
 * Created by Vincent on 2020/1/10.
 */
class UiProfileInputDialogFragment: BaseDialogFragment<DialogTextInputBinding>(), View.OnClickListener {

    private lateinit var callback: OnValueSetCallback

    companion object {
        fun newInstance(title: String, hint: String, typeId: Int, isPassword: Boolean): UiProfileInputDialogFragment {
            return UiProfileInputDialogFragment().apply {
                arguments = Bundle().also {
                    it.putString(Const.BUNDLE_TITLE, title)
                    it.putString(Const.BUNDLE_HINT, hint)
                    it.putInt(Const.BUNDLE_TYPE, typeId)
                    it.putBoolean(Const.BUNDLE_IS_PASSWORD, isPassword)
                }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.dialog_text_input

    fun setOnValueSetCallback(callback: OnValueSetCallback) {
        this.callback = callback
    }

    override fun setDialogWindowAttrs(window: Window) {
        window.run {
            val background = ContextCompat.getDrawable(AppController.instance.applicationContext, R.drawable.background_white_corner_10dp)
            setBackgroundDrawable(background)
        }
    }

    override fun init() {
        mBinding.buttonCancel.setOnClickListener(this)
        mBinding.buttonOk.setOnClickListener(this)
        getBundleAndSetInfo()
        focusEditAndOpenKeyboard()
    }

    private fun getBundleAndSetInfo() {
        arguments?.run {
            mBinding.textTitle.text = getString(Const.BUNDLE_TITLE)

            mBinding.editInput.let {
                if (getBoolean(Const.BUNDLE_IS_PASSWORD)) {
                    it.transformationMethod = PasswordTransformationMethod.getInstance()
                    mBinding.layoutTextInput.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                }
                else {
                    val hint = getString(Const.BUNDLE_HINT)
                    it.hint = hint
                    mBinding.layoutTextInput.hint = hint
                }

                if (getInt(Const.BUNDLE_TYPE) == R.id.widget_remotePort) {
                    it.inputType = InputType.TYPE_CLASS_NUMBER
                }
            }
        }
    }

    private fun focusEditAndOpenKeyboard() {
        mBinding.editInput.run {
            requestFocus()
            postDelayed({
                AppController.instance.hideKeyboardByGivenView(false, this)
            } , 500L)
        }
    }

    private fun checkInput() {
        val input = mBinding.editInput.text.toString()

        if (input.isEmpty()) {
            focusEditAndOpenKeyboard()
        }
        else {
            bringTheValueBack(input)
        }
    }

    private fun bringTheValueBack(input: String) {
        arguments?.run {
            callback.onValueSet(getInt(Const.BUNDLE_TYPE), input)
        }

        dismiss()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_cancel -> dismiss()
            R.id.button_ok -> checkInput()
        }
    }
}
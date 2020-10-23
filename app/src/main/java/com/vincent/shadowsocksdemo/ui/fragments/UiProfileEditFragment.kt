package com.vincent.shadowsocksdemo.ui.fragments

import android.os.Bundle
import android.view.View
import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.callbacks.OnValueSetCallback
import com.vincent.shadowsocksdemo.databinding.FragmentProfileEditBinding
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.ui.bases.BaseFragment
import com.vincent.shadowsocksdemo.ui.dialog.UiProfileInputDialogFragment
import com.vincent.shadowsocksdemo.utilities.LogUtil
import com.vincent.shadowsocksdemo.utilities.MenuActions
import com.vincent.shadowsocksdemo.widgets.SelectionItemWidget

/**
 * Created by Vincent on 2020/1/9.
 */
class UiProfileEditFragment: BaseFragment<FragmentProfileEditBinding>(), OnValueSetCallback {

    private var profile: Profile? = null

    //private var dialog: AlertDialog? = null

    companion object {
        fun newInstance(profile: Profile): UiProfileEditFragment {
            return UiProfileEditFragment().apply {
                arguments = Bundle().also { it.putParcelable(Const.BUNDLE_PARCELABLE, profile) }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_profile_edit

    override fun getTitleRes(): Int = R.string.profile_config

    override fun getMenuOptions(): IntArray? = intArrayOf(MenuActions.ACTION_DONE)

    override fun init() {
        getBundleAndSetProfile()
    }

    private fun getBundleAndSetProfile() {
        arguments?.run {
            profile = getParcelable(Const.BUNDLE_PARCELABLE)
            profile?.let { mBinding.item = it }
        }
        mBinding.click = this
    }

    private fun openInputDialog(widget: SelectionItemWidget) {
        /*
        if (widget.id != R.id.widget_encMethod) {

        }
        else {
            val bindingView = DataBindingUtil.inflate<DialogTextInputBinding>(LayoutInflater.from(context), R.layout.dialog_text_input, view as ViewGroup, false)
            dialog = DialogHelper.getCustomViewDialog(context!!, bindingView.root)

            bindingView.apply {
                textTitle.text = widget.getTitle()
                editInput.hint = widget.getSnippet()

                if (widget.isPassword()) {
                    editInput.let {
                        it.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        it.transformationMethod = PasswordTransformationMethod.getInstance()
                        layoutTextInput.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                    }
                }

                buttonCancel.setOnClickListener(this@UiProfileEditFragment)
                buttonOk.setOnClickListener(this@UiProfileEditFragment)
            }
        }
        */

        if (!widget.isSpinner()) {
            UiProfileInputDialogFragment.newInstance(widget.getTitle(), widget.getSnippet(), widget.id, widget.isPassword()).apply {
                setOnValueSetCallback(this@UiProfileEditFragment)
                openDialogFragment(this, false)
            }
        }
    }

    override fun onValueSet(type: Int, value: String) {
        when (type) {
            R.id.widget_profileName -> {
                profile?.name = value
            }
            R.id.widget_server -> {
                profile?.host = value
            }
            R.id.widget_remotePort -> {
                profile?.remotePort = Integer.parseInt(value)
            }
            R.id.widget_password -> {
                profile?.password = value
            }
        }
        mBinding.item = profile
    }

    override fun onClick(v: View) {
        if (v is SelectionItemWidget) {
            openInputDialog(v)
        }
    }

    override fun onMenuItemClick(action: Int) {
        when (action) {
            MenuActions.ACTION_DONE -> {
                saveProfile()
            }
        }
    }

    private fun saveProfile() {
        profile?.run {
            method = mBinding.widgetEncMethod.getSpinnerValue()

            ProfileManager.updateProfile(this)

            LogUtil.i(TAG, "onSavProfile!!!id: $id")

            popBack(null)
        }
    }

    override fun clearReference() {

    }
}
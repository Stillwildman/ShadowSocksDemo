package com.vincent.shadowsocksdemo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.shadowsocks.database.Profile
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.bases.BaseFragment
import com.vincent.shadowsocksdemo.databinding.FragmentProfileEditBinding
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.utilities.MenuActions

/**
 * Created by Vincent on 2020/1/9.
 */
class UiProfileEditFragment: BaseFragment<FragmentProfileEditBinding>() {

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
            getParcelable<Profile>(Const.BUNDLE_PARCELABLE)?.let { mBinding.item = it }
        }
        mBinding.click = this
    }

    override fun onClick(v: View?) {
        Log.i(TAG, "onClick!!!")
    }

    override fun onMenuItemClick(action: Int) {
        when (action) {
            MenuActions.ACTION_DONE -> {

            }
        }
    }

    override fun clearReference() {

    }
}
package com.vincent.shadowsocksdemo.ui.fragments.login

import android.view.View
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.databinding.FragmentLoginMainBinding
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.ui.bases.BaseFragment

/**
 * Created by Vincent on 2020/1/31.
 */
class UiLoginMainFragment : BaseFragment<FragmentLoginMainBinding>() {


    override fun getLayoutId(): Int = R.layout.fragment_login_main

    override fun getTitleRes(): Int = 0

    override fun getMenuOptions(): IntArray? = null

    override fun init() {
        mBinding.textForgotPass.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.text_forgotPass -> goToFragment(UiLoginForgotPassFragment(), false, Const.BACK_LOGIN)
        }
    }

    override fun clearReference() {

    }
}
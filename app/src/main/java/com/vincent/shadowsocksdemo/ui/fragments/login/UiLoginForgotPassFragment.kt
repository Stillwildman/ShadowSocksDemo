package com.vincent.shadowsocksdemo.ui.fragments.login

import android.view.View
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.databinding.FragmentLoginForgotPassBinding
import com.vincent.shadowsocksdemo.ui.bases.BaseFragment

/**
 * Created by Vincent on 2020/2/3.
 */
class UiLoginForgotPassFragment : BaseFragment<FragmentLoginForgotPassBinding>() {



    override fun getLayoutId(): Int = R.layout.fragment_login_forgot_pass

    override fun getTitleRes(): Int = 0

    override fun getMenuOptions(): IntArray? = null

    override fun init() {

    }

    override fun onClick(v: View?) {

    }

    override fun clearReference() {

    }
}
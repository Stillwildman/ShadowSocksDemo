package com.vincent.shadowsocksdemo.ui.fragments

import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.bases.BaseFragment
import com.vincent.shadowsocksdemo.databinding.FragmentHomeBinding

/**
 * Created by Vincent on 2020/1/6.
 */
class UiHomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getTitleRes(): Int = R.string.app_name

    override fun getMenuOptions(): IntArray? = null

    override fun init() {

    }

    override fun clearReference() {

    }
}
package com.vincent.shadowsocksdemo.ui.fragments

import android.util.Log
import android.view.View
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.bases.BaseFragment
import com.vincent.shadowsocksdemo.callbacks.OnSelectionDoneCallback
import com.vincent.shadowsocksdemo.databinding.FragmentHomeBinding
import com.vincent.shadowsocksdemo.model.Const


/**
 * Created by Vincent on 2020/1/6.
 */
class UiHomeFragment : BaseFragment<FragmentHomeBinding>(), OnSelectionDoneCallback {

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getTitleRes(): Int = R.string.app_name

    override fun getMenuOptions(): IntArray? = null

    override fun init() {
        mBinding.buttonPower.setOnClickListener(this)
        mBinding.layoutNodeSelector.root.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_power -> {
                switchPowerState()
            }
            R.id.layout_nodeSelector -> {
                goToFragment(UiProfileSelectionFragment().apply { setOnSelectionDoneCallback(this@UiHomeFragment) }, false, Const.BACK_HOME)
            }
        }
    }

    private fun switchPowerState() {
        mBinding.buttonPower.run { isSelected = !isSelected }

        Log.i(TAG, "Power onClick!!!")
    }

    override fun onSelectionDone(name: String) {
        setSelectedNodeName(name)
    }

    private fun setSelectedNodeName(name : String) {
        mBinding.layoutNodeSelector.textNodeName.text = name
    }

    override fun clearReference() {

    }
}
package com.vincent.shadowsocksdemo.callbacks

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

/**
 * Created by Vincent on 2020/1/6.
 */
interface FragmentInterface {

    fun onFragmentSetTitle(titleRes: Int)

    fun onFragmentSetMenu(actions: IntArray?)

    fun onFragmentOpen(instance: Fragment, useReplace: Boolean, backName: String?)

    fun onOpenDialogFragment(instance: DialogFragment, isLoadingDialog : Boolean)

    fun onDialogFragmentDismiss()

    fun onFragmentPopBack(backName: String?)

    fun onFragmentWantSomeCircle(inLoading: Boolean)

    fun onSetOptionsClickCallback(callback : OnOptionsClickCallback)
}
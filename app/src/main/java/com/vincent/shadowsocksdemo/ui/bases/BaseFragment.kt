package com.vincent.shadowsocksdemo.ui.bases

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.vincent.shadowsocksdemo.AppController
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.callbacks.FragmentInterface
import com.vincent.shadowsocksdemo.callbacks.OnOptionsClickCallback
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.ui.dialog.LoadingDialogFragment
import com.vincent.shadowsocksdemo.utilities.LogUtil

/**
 * Created by Vincent on 2020/1/6.
 */
abstract class BaseFragment<bindingView : ViewDataBinding> : Fragment(), OnOptionsClickCallback, View.OnClickListener, Const {

    protected var TAG = javaClass.simpleName

    protected abstract fun getLayoutId() : Int
    protected abstract fun getTitleRes(): Int
    protected abstract fun getMenuOptions(): IntArray?
    protected abstract fun init()
    protected abstract fun clearReference()

    protected lateinit var mBinding : bindingView

    private lateinit var fragmentInterface : FragmentInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            fragmentInterface = context as FragmentInterface
        }
        catch (e : ClassCastException) {
            e.printStackTrace()
            LogUtil.e(TAG, context.javaClass.simpleName + " must implement " + FragmentInterface::class.java.simpleName)
        }

        LogUtil.d(TAG, "onAttach!!!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(TAG, "onCreate!!!")
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        init()
        LogUtil.d(TAG, "Init!!! (onCreateView)")

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.background = ContextCompat.getDrawable(AppController.instance.applicationContext, R.drawable.background_map)
        view.isClickable = true

        LogUtil.d(TAG, "onViewCreated!!!")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d(TAG, "onStart!!!")
    }

    override fun onResume() {
        super.onResume()
        setTitleAndMenu()
        setActivityOptionsCallback()
        LogUtil.d(TAG, "onResume!!!")
    }

    private fun setTitleAndMenu() {
        fragmentInterface.onFragmentSetTitle(getTitleRes())
        fragmentInterface.onFragmentSetMenu(getMenuOptions())
    }

    private fun setActivityOptionsCallback() {
        fragmentInterface.onSetOptionsClickCallback(this)
    }

    protected fun goToFragment(instance : Fragment, useReplace : Boolean, backName : String?) = fragmentInterface.onFragmentOpen(instance, useReplace, backName)

    protected fun openDialogFragment(instance: DialogFragment, isLoadingDialog : Boolean) {
        fragmentInterface.onOpenDialogFragment(instance, isLoadingDialog)
    }

    protected fun showLoadingDialog() {
        openDialogFragment(LoadingDialogFragment(), true)
    }

    protected fun dismissLoadingDialog() {
        fragmentInterface.onDialogFragmentDismiss()
    }

    protected fun popBack(backName: String?) = fragmentInterface.onFragmentPopBack(backName)

    protected fun showLoadingCircle(isShow : Boolean) = fragmentInterface.onFragmentWantSomeCircle(isShow)

    override fun onPause() {
        super.onPause()
        LogUtil.d(TAG, "onPause!!!")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d(TAG, "onStop!!!")
    }

    override fun onFragmentBackPressed(): Boolean {
        popBack(null)
        return true
    }

    override fun onMenuItemClick(action: Int) {
        LogUtil.i(TAG, "onMenuItemClick!!! Action: $action")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearReference()
        LogUtil.d(TAG, "onDestroyView!!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "onDestroy!!!")
    }
}
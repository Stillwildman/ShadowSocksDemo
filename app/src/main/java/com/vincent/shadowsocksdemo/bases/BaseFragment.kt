package com.vincent.shadowsocksdemo.bases

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.vincent.shadowsocksdemo.callbacks.FragmentInterface
import com.vincent.shadowsocksdemo.callbacks.OnOptionsClickCallback
import com.vincent.shadowsocksdemo.model.Const

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
            Log.e(TAG, context.javaClass.simpleName + " must implement " + FragmentInterface::class.java.simpleName)
        }
        catch (e : ClassCastException) {
            e.printStackTrace()
        }

        Log.d(TAG, "onAttach!!!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate!!!")
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        init()
        Log.d(TAG, "Init!!! (onCreateView)")

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBackgroundColor(Color.WHITE)
        view.isClickable = true

        Log.d(TAG, "onViewCreated!!!")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart!!!")
    }

    override fun onResume() {
        super.onResume()
        setTitleAndMenu()
        setActivityOptionsCallback()
        Log.d(TAG, "onResume!!!")
    }

    private fun setTitleAndMenu() {
        fragmentInterface.onFragmentSetTitle(getTitleRes())
        fragmentInterface.onFragmentSetMenu(getMenuOptions())
    }

    private fun setActivityOptionsCallback() {
        fragmentInterface.onSetOptionsClickCallback(this)
    }

    protected fun goToFragment(instance : Fragment, useReplace : Boolean, backName : String?) = fragmentInterface.onFragmentOpen(instance, useReplace, backName)

    protected fun popBack(backName: String?) = fragmentInterface.onFragmentPopBack(backName)

    protected fun showLoadingCircle(isShow : Boolean) = fragmentInterface.onFragmentWantSomeCircle(isShow)

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause!!!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop!!!")
    }

    override fun onFragmentBackPressed(): Boolean {
        popBack(null)
        return true
    }

    override fun onMenuItemClick(action: Int) {
        Log.i(TAG, "onMenuItemClick!!! Action: $action")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearReference()
        Log.d(TAG, "onDestroyView!!!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy!!!")
    }
}
package com.vincent.shadowsocksdemo.bases

import android.util.Log
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.callbacks.FragmentInterface
import com.vincent.shadowsocksdemo.callbacks.OnOptionsClickCallback

/**
 * Created by Vincent on 2020/1/3.
 */
abstract class BaseFragmentActivity<bindingView : ViewDataBinding> : BaseActivity<bindingView>(), FragmentManager.OnBackStackChangedListener, FragmentInterface {

    protected abstract fun onBackToHome()

    private lateinit var optionsClickCallback : OnOptionsClickCallback

    protected val fm : FragmentManager by lazy { supportFragmentManager }

    private var backStackCount : Int = 0

    override fun init() {
        fm.addOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {
        val backStackCount = fm.backStackEntryCount

        Log.i(TAG, "onBackStackChanged: $backStackCount")

        if (backStackCount == 1) {
            toggle?.run { isDrawerIndicatorEnabled = true }
            onBackToHome()
        }
        else {
            toggle?.run { isDrawerIndicatorEnabled = false }
        }

        if (backStackCount < this.backStackCount) {
            resumeFragment()
        }

        this.backStackCount = backStackCount
    }

    private fun resumeFragment() = fm.findFragmentById(R.id.fragment_container)?.run { onResume() }

    private fun isFragmentsMoreThanOne() : Boolean = fm.backStackEntryCount > 1

    private fun goToFragment(instance : Fragment, useReplace : Boolean, @Nullable backName : String?) {
        fm.findFragmentById(R.id.fragment_container)?.run {
            if (equals(instance)) return
        }

        if (useReplace) {
            fm.beginTransaction().replace(R.id.fragment_container, instance).addToBackStack(backName).commit()
        }
        else {
            fm.beginTransaction().add(R.id.fragment_container, instance).addToBackStack(backName).commit()
        }
    }

    private fun popBack(@Nullable backName : String?) {
        if (isFragmentsMoreThanOne()) {
            if (backName == null) {
                fm.popBackStackImmediate()
            }
            else {
                fm.popBackStackImmediate(backName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }

    protected fun clearFragments() {
        if (isFragmentsMoreThanOne()) {
            val backLevel = fm.getBackStackEntryAt(1)
            fm.popBackStack(backLevel.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onFragmentSetTitle(titleRes: Int) {
        this.setTitle(titleRes)
    }

    override fun onFragmentSetMenu(actions: IntArray?) {
        // TODO
    }

    override fun onFragmentOpen(instance: Fragment, useReplace: Boolean, backName: String?) {
        goToFragment(instance, useReplace, backName)
    }

    override fun onFragmentPopBack(backName: String?) {
        popBack(backName)
    }

    override fun onFragmentWantSomeCircle(inLoading: Boolean) {
        showLoadingCircle(inLoading)
    }

    override fun onSetOptionsClickCallback(callback: OnOptionsClickCallback) {
        optionsClickCallback = callback
    }

    override fun hasFragmentBackStack(): Boolean = isFragmentsMoreThanOne()

    override fun onMenuItemClick(itemId: Int) {
        optionsClickCallback.onMenuItemClick(itemId)
    }

    override fun onBackPressed() {
        if (!optionsClickCallback.onFragmentBackPressed()) {
            super.onBackPressed()
        }
    }
}
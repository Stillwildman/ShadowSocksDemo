package com.vincent.shadowsocksdemo.bases

import android.util.Log
import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.vincent.shadowsocksdemo.R

/**
 * Created by Vincent on 2020/1/3.
 */
abstract class BaseFragmentActivity<bindingView : ViewDataBinding> : BaseActivity<bindingView>(), FragmentManager.OnBackStackChangedListener {

    protected abstract fun onFragmentsAllGone()

    protected val fm : FragmentManager by lazy { supportFragmentManager }

    private var backStackCount : Int = 0

    override fun init() {
        fm.addOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {
        val backStackCount = fm.backStackEntryCount

        Log.i(TAG, "onBackStackChanged: $backStackCount")

        if (backStackCount == 0) {
            toggle?.run { isDrawerIndicatorEnabled = true }
            onFragmentsAllGone()
        }
        else {
            toggle?.run { isDrawerIndicatorEnabled = false }

            if (backStackCount < this.backStackCount) {
                resumeFragment()
            }
        }

        this.backStackCount = backStackCount
    }

    private fun resumeFragment() = fm.findFragmentById(R.id.fragment_container)?.run { onResume() }

    private fun hasFragments() : Boolean = fm.backStackEntryCount > 0

    protected fun goToFragment(instance : Fragment, useReplace : Boolean, @Nullable backName : String?) {
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

    protected fun popBack(@Nullable backName : String?) {
        if (hasFragments()) {
            if (backName == null) {
                fm.popBackStackImmediate()
            }
            else {
                fm.popBackStackImmediate(backName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }

    protected fun clearFragments() {
        if (hasFragments()) {
            val backLevel = fm.getBackStackEntryAt(0)
            fm.popBackStack(backLevel.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}
package com.vincent.shadowsocksdemo.ui.bases

import androidx.annotation.Nullable
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.callbacks.FragmentInterface
import com.vincent.shadowsocksdemo.callbacks.OnOptionsClickCallback
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.model.items.EventMessage
import com.vincent.shadowsocksdemo.utilities.LogUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Vincent on 2020/1/3.
 */
abstract class BaseFragmentActivity<bindingView : ViewDataBinding> : BaseActivity<bindingView>(), FragmentManager.OnBackStackChangedListener, FragmentInterface {

    protected abstract fun onBackToFirstFragment()

    protected abstract fun onInitDone()

    private lateinit var optionsClickCallback : OnOptionsClickCallback

    private val fm : FragmentManager by lazy { supportFragmentManager }

    private var backStackCount : Int = 0

    override fun init() {
        fm.addOnBackStackChangedListener(this)
        onInitDone()

        EventBus.getDefault().register(this)
    }

    override fun onBackStackChanged() {
        val backStackCount = fm.backStackEntryCount

        LogUtil.i(TAG, "onBackStackChanged: $backStackCount")

        if (backStackCount == 1) {
            toggle?.run { isDrawerIndicatorEnabled = true }
            onBackToFirstFragment()
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

    private fun openDialogFragment(instance: DialogFragment, isLoadingDialog : Boolean) {
        fm.beginTransaction().let {
            if (isLoadingDialog) {
                instance.isCancelable = false
            }
            else {
                it.addToBackStack(Const.BACK_DIALOG)
            }
            instance.show(it, Const.DIALOG_FRAGMENT)
        }
    }

    fun dismissDialogFragment() {
        fm.findFragmentByTag(Const.DIALOG_FRAGMENT)?.let {
            fm.beginTransaction().remove(it)
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
        if (titleRes != 0) {
            this.setTitle(titleRes)
        }
    }

    override fun onFragmentSetMenu(actions: IntArray?) {
        setMenuOptions(actions)
    }

    override fun onFragmentOpen(instance: Fragment, useReplace: Boolean, backName: String?) {
        goToFragment(instance, useReplace, backName)
    }

    override fun onOpenDialogFragment(instance: DialogFragment, isLoadingDialog : Boolean) {
        openDialogFragment(instance, isLoadingDialog)
    }

    override fun onDialogFragmentDismiss() {
        dismissDialogFragment()
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
        LogUtil.i(TAG, "onBackPressed!!!")

        if (!isFragmentsMoreThanOne() || !optionsClickCallback.onFragmentBackPressed()) {
            super.onBackPressed()
        }
    }

    @Subscribe
    fun onNetworkEventChanged(eventMessage : EventMessage) {
        when (eventMessage.eventType) {
            Const.EVENT_NETWORK_DONE -> dismissDialogFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
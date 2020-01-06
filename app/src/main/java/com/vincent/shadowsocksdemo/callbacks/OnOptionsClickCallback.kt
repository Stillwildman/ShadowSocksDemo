package com.vincent.shadowsocksdemo.callbacks

/**
 * Created by Vincent on 2020/1/6.
 */
interface OnOptionsClickCallback {

    /**
     * @return true: Consume the back press event in the fragment scope.<br>
     * false: Do the FragmentActivity default onBackPressed() action.
     */
    fun onFragmentBackPressed() : Boolean

    fun onMenuItemClick(action : Int)

}
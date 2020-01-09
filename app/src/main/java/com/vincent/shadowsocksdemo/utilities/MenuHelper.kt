package com.vincent.shadowsocksdemo.utilities

import android.view.Menu
import android.view.MenuItem
import com.vincent.shadowsocksdemo.R

/**
 * Created by Vincent on 2020/1/9.
 */
object MenuHelper: MenuActions {

    fun setMenuOptions(menu: Menu, actions: IntArray?) {
        menu.clear()

        if (actions == null) {
            return
        }

        var titleRes = 0
        var iconRes = 0

        for (action in actions) {
            when (action) {
                MenuActions.ACTION_DONE -> {
                    titleRes = R.string.apply
                    iconRes = R.drawable.ic_done
                }
            }

            if (menu.findItem(action) == null) {
                menu.add(Menu.NONE, action, Menu.NONE, titleRes)
                    .setIcon(iconRes)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            }
        }
    }

    fun removeMenuOptions(menu: Menu, vararg actions: Int) {
        for (action in actions) {
            menu.removeItem(action)
        }
    }
}
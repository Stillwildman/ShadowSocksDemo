package com.vincent.shadowsocksdemo.utilities

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.callbacks.OnSelectionDoneCallback
import com.vincent.shadowsocksdemo.model.Const

/**
 * Created by Vincent on 2020/1/10.
 */
object DialogHelper {

    private fun showDialog(dialogBuilder: AlertDialog.Builder, customizedBackground: Boolean, widthProportion: Float): AlertDialog {
        val dialog = dialogBuilder.create()

        if (widthProportion != 0f && widthProportion != 1f) {
            dialog.setOnShowListener {
                dialog.window?.run {
                    attributes.width = (Utility.getScreenWidth() * widthProportion).toInt()
                    attributes = attributes
                }
                dialog.setOnShowListener(null)
            }
        }
        if (customizedBackground) {
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.show()

        return dialog
    }

    fun getCustomViewDialog(context: Context, view: View): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder.setView(view)

        return showDialog(dialogBuilder, true, 0f)
    }

    fun showSimpleEditDialog(context: Context, callback: OnSelectionDoneCallback) {
        val dialogBuilder = AlertDialog.Builder(context)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_simple_edit, null)

        dialogBuilder.setView(view)

        val dialog = showDialog(dialogBuilder, false, 0f)

        val edit = view.findViewById<EditText>(R.id.edit_input)

        edit.setText(Const.DEFAULT_SS_URL)

        val clickListener = View.OnClickListener { v ->
            when (v.id) {
                R.id.button_cancel -> dialog.dismiss()
                R.id.button_ok -> {
                    callback.onSelectionDone(edit.text.toString())
                    dialog.dismiss()
                }
            }
        }
        view.findViewById<View>(R.id.button_cancel).setOnClickListener(clickListener)
        view.findViewById<View>(R.id.button_ok).setOnClickListener(clickListener)
    }
}
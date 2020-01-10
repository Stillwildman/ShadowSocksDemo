package com.vincent.shadowsocksdemo.widgets

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.vincent.shadowsocksdemo.AppController
import com.vincent.shadowsocksdemo.R

/**
 * Created by Vincent on 2020/1/9.
 */
object TwoWayBindingObject {

    @JvmStatic
    @BindingAdapter("snippetText")
    fun setSnippetText(widget: SelectionItemWidget, snippetString: String?) {
        (widget.snippetView as TextView).text = if (snippetString.isNullOrEmpty()) {
            AppController.instance.getString(R.string.value_not_set)
        }
        else {
            snippetString
        }
    }

}
package com.vincent.shadowsocksdemo.widgets

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
        widget.snippet.text = if (snippetString == null || snippetString.isEmpty()) {
            AppController.instance.getString(R.string.value_not_set)
        }
        else {
            snippetString
        }
    }

}
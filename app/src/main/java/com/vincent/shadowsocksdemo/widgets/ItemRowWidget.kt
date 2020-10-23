package com.vincent.shadowsocksdemo.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.vincent.shadowsocksdemo.AppController
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.databinding.InflateCommonItemBinding

/**
 * Created by Vincent on 2020/2/5.
 */
class ItemRowWidget : FrameLayout {

    private val bindingView by lazy {
        DataBindingUtil.inflate<InflateCommonItemBinding>(LayoutInflater.from(context), R.layout.inflate_common_item, this, false)
    }

    private val checkedListener by lazy {
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->

        }
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(attrs)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ItemRowWidget)

        val iconRes = attr.getResourceId(R.styleable.ItemRowWidget_itemIconRes, 0)
        val text = attr.getString(R.styleable.ItemRowWidget_text) ?: ""
        val showSwitch = attr.getBoolean(R.styleable.ItemRowWidget_hasSwitch, false)

        initViews(iconRes, text, showSwitch)

        attr.recycle()
    }

    private fun initViews(iconRes: Int, title: String, showSwitch: Boolean) {
        bindingView.imageIcon.setImageDrawable(ContextCompat.getDrawable(AppController.instance.applicationContext, iconRes))
        bindingView.textItemName.text = title

        if (showSwitch) {
            bindingView.switchItem.visibility = View.VISIBLE
            bindingView.switchItem.setOnCheckedChangeListener(checkedListener)
        }
        else {
            bindingView.switchItem.visibility = View.GONE
            bindingView.switchItem.setOnCheckedChangeListener(null)
        }

        this.addView(bindingView.root)
    }

    fun setIconRes(iconRes: Int) {
        bindingView.imageIcon.setImageDrawable(ContextCompat.getDrawable(AppController.instance.applicationContext, iconRes))
    }

    fun setText(text: String) {
        bindingView.textItemName.text = text
    }

    fun showSwitch() {
        bindingView.switchItem.visibility = View.VISIBLE
        bindingView.switchItem.setOnCheckedChangeListener(checkedListener)
    }

    fun setSwitchChecked(isChecked: Boolean) {
        bindingView.switchItem.isChecked = isChecked
    }

    fun setOnCheckedChangedListener(onCheckedChangeListener: CompoundButton.OnCheckedChangeListener) {
        bindingView.switchItem.setOnCheckedChangeListener(onCheckedChangeListener)
    }
}
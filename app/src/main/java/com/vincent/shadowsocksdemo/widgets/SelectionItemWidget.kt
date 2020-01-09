package com.vincent.shadowsocksdemo.widgets

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.vincent.shadowsocksdemo.AppController
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.utilities.DrawableUtil


/**
 * Created by Vincent on 2020/1/9.
 */
class SelectionItemWidget: ConstraintLayout {

    lateinit var snippet: TextView

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        getAttrs(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        getAttrs(attrs)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.SelectionItemWidget)

        val iconRes: Int = attr.getResourceId(R.styleable.SelectionItemWidget_iconRes, 0)
        val titleString: String? = attr.getString(R.styleable.SelectionItemWidget_titleText)
        val snippetString: String? = attr.getString(R.styleable.SelectionItemWidget_snippetText)
        val showSwitch: Boolean = attr.getBoolean(R.styleable.SelectionItemWidget_showSwitch, false)
        val isPassword: Boolean = attr.getBoolean(R.styleable.SelectionItemWidget_isPassword, false)

        initViews(iconRes, titleString, snippetString, showSwitch, isPassword)

        attr.recycle()
    }

    private fun initViews(iconRes: Int, titleString: String?, snippetString: String?, showSwitch: Boolean, isPassword: Boolean) {
        this.isClickable = true
        this.isFocusable = true

        TypedValue().let {
            context.theme.resolveAttribute(R.attr.selectableItemBackground, it, true)
            setBackgroundResource(it.resourceId)
        }

        val icon = AppCompatImageView(context).apply {
            id = R.id.icon_id

            val size = getPxSize(R.dimen.icon_common_size)

            layoutParams = LayoutParams(size, size)

            setImageDrawable(DrawableUtil.getVectorDrawable(iconRes))
        }

        val textSize = getPxSize(R.dimen.font_text_size_m).toFloat()

        val title = TextView(context).apply {
            id = R.id.title_id

            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT)

            text = titleString
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            setTextColor(ContextCompat.getColor(AppController.instance.applicationContext, R.color.md_grey_800))
        }

        snippet = TextView(context).apply {
            id = R.id.snippet_id

            layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT)

            if (snippetString != null) {
                text = snippetString
            }
            else {
                setText(R.string.value_not_set)
            }

            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            setTextColor(ContextCompat.getColor(AppController.instance.applicationContext, R.color.md_grey_600))

            if (isPassword) {
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        val switcher = SwitchCompat(context).apply {
            id = R.id.switch_id
            visibility = if (showSwitch) View.VISIBLE else View.GONE
        }

        this.run {
            addView(icon)
            addView(title)
            addView(snippet)
            addView(switcher)

            setPadding(getPxSize(R.dimen.padding_size_xl))
        }

        ConstraintSet().apply {
            clone(this@SelectionItemWidget)

            connect(icon.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(icon.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(icon.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)

            connect(title.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(title.id, ConstraintSet.BOTTOM, snippet.id, ConstraintSet.TOP)
            connect(title.id, ConstraintSet.START, icon.id, ConstraintSet.END, getPxSize(R.dimen.padding_size_xl))
            connect(title.id, ConstraintSet.END, switcher.id, ConstraintSet.START, getPxSize(R.dimen.padding_size_l))

            connect(snippet.id, ConstraintSet.TOP, title.id, ConstraintSet.BOTTOM, getPxSize(R.dimen.padding_size_m))
            connect(snippet.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(snippet.id, ConstraintSet.START, title.id, ConstraintSet.START)
            connect(snippet.id, ConstraintSet.END, title.id, ConstraintSet.END)

            connect(switcher.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            connect(switcher.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
            connect(switcher.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            applyTo(this@SelectionItemWidget)
        }
    }

    private fun getPxSize(@DimenRes dimenRes: Int): Int = AppController.instance.applicationContext.resources.getDimensionPixelSize(dimenRes)

    fun setSnippet(snippet: String) {
        this.snippet.text = snippet
    }
}
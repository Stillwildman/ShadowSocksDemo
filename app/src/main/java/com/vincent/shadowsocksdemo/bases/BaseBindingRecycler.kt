package com.vincent.shadowsocksdemo.bases

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * The DataBinding Base for Recycler's adapter.
 *
 * @author Vincent Chang (2018/3/26)
 */
abstract class BaseBindingRecycler<T : ViewDataBinding> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val TAG = javaClass.simpleName

    protected abstract fun getLayoutId(): Int

    protected abstract fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: T, position: Int)

    protected abstract fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: T, position: Int, payload: Any?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: T = DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayoutId(), parent, false)
        return BindingViewHolder(binding)
    }

    inner class BindingViewHolder(val bindingView: T) : RecyclerView.ViewHolder(bindingView.root)

    @Suppress("UNCHECKED_CAST", "RemoveRedundantQualifierName")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindingViewHolder(holder, (holder as BaseBindingRecycler<T>.BindingViewHolder).bindingView, position)
    }

    @Suppress("UNCHECKED_CAST", "RemoveRedundantQualifierName")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
        else {
            onBindingViewHolder(holder, (holder as BaseBindingRecycler<T>.BindingViewHolder).bindingView, position, payloads[0])
        }
    }
}
package com.vincent.shadowsocksdemo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.vincent.shadowsocksdemo.R

/**
 * Created by Vincent on 2020/1/10.
 */
class KeyValueListAdapter(private val keyValueList: List<Pair<String, String>>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder

        var view: View? = convertView

        if (view == null) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.inflate_simple_spinner_text_align_start, parent, false)
            holder = ViewHolder()
            holder.spinnerText = view.findViewById(R.id.text_SpinnerText)

            view.tag = holder
        }
        else {
            holder = view.tag as ViewHolder
        }

        holder.spinnerText?.text = keyValueList[position].first

        return view!!
    }

    override fun getItem(position: Int): Any = keyValueList[position].second

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = keyValueList.size

    private class ViewHolder {
        var spinnerText: TextView? = null
    }
}
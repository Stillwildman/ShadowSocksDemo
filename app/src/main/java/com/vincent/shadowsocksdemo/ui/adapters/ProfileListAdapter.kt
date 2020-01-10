package com.vincent.shadowsocksdemo.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.shadowsocks.database.Profile
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.databinding.InflateProfileListRowBinding
import com.vincent.shadowsocksdemo.ui.bases.BaseBindingRecycler

/**
 * Created by Vincent on 2020/1/8.
 */
class ProfileListAdapter(private val clickListener: View.OnClickListener) : BaseBindingRecycler<InflateProfileListRowBinding>() {

    private val profileList : MutableList<Profile> by lazy { arrayListOf<Profile>() }

    fun updateList(profileList: List<Profile>) {
        this.profileList.run {
            clear()
            addAll(profileList)
        }
        this.notifyDataSetChanged()
    }

    override fun getLayoutId(): Int = R.layout.inflate_profile_list_row

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateProfileListRowBinding, position: Int) {
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(clickListener)

        bindingView.run {
            textProfileName.text = profileList[position].name
            textHost.text = profileList[position].host

            buttonDelete.tag = position
            buttonDelete.setOnClickListener(clickListener)

            buttonEdit.tag = position
            buttonEdit.setOnClickListener(clickListener)
        }
    }

    override fun onBindingViewHolder(holder: RecyclerView.ViewHolder, bindingView: InflateProfileListRowBinding, position: Int, payload: Any?) {

    }

    fun getItem(position: Int) = profileList[position]

    override fun getItemCount(): Int = profileList.size
}
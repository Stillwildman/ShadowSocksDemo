package com.vincent.shadowsocksdemo.ui.fragments

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.shadowsocks.Core
import com.github.shadowsocks.database.PrivateDatabase
import com.github.shadowsocks.database.Profile
import com.github.shadowsocks.database.ProfileManager
import com.vincent.shadowsocksdemo.R
import com.vincent.shadowsocksdemo.bases.BaseFragment
import com.vincent.shadowsocksdemo.callbacks.OnSelectionDoneCallback
import com.vincent.shadowsocksdemo.databinding.FragmentProfileSelectionBinding
import com.vincent.shadowsocksdemo.model.Const
import com.vincent.shadowsocksdemo.ui.adapters.ProfileListAdapter

/**
 * Created by Vincent on 2020/1/8.
 */
class UiProfileSelectionFragment : BaseFragment<FragmentProfileSelectionBinding>() {

    private lateinit var callback: OnSelectionDoneCallback

    fun setOnSelectionDoneCallback(callback : OnSelectionDoneCallback) {
        this.callback = callback
    }

    override fun getLayoutId(): Int = R.layout.fragment_profile_selection

    override fun getTitleRes(): Int = 0

    override fun getMenuOptions(): IntArray? = null

    override fun init() {
        mBinding.buttonAddNew.setOnClickListener(this)
        initRecycler()
    }

    private fun initRecycler() {
        context?.let {
            mBinding.recyclerProfiles.apply {
                layoutManager = LinearLayoutManager(it)
                addItemDecoration(DividerItemDecoration(it, LinearLayout.VERTICAL))
                adapter = ProfileListAdapter(this@UiProfileSelectionFragment)

                getProfileList()
            }
        }
    }

    private fun getProfileList() {
        val liveList = PrivateDatabase.profileDao.liveList()

        liveList.observe(this, Observer { profileList ->
            setProfileList(profileList)
            Log.i(TAG, "on Profile list data Changed!!! Size: ${profileList.size}")
        })
    }

    private fun setProfileList(profileList: List<Profile>) {
        getAdapter()?.updateList(profileList)
    }

    private fun getAdapter(): ProfileListAdapter? {
        mBinding.recyclerProfiles.adapter?.apply {
            return this as ProfileListAdapter
        }
        return null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_edit -> openProfileEditFragment(v.tag as Int)
            R.id.button_addNew -> openProfileEditFragment(null)
        }
    }

    private fun openProfileEditFragment(position: Int?) {
        val profile = if (position != null) {
            getAdapter()?.getItem(position)
        }
        else {
            ProfileManager.createProfile(Profile().also { Core.currentProfile?.first?.copyFeatureSettingsTo(it) })
        }

        goToFragment(UiProfileEditFragment.newInstance(profile!!), false, Const.BACK_PROFILE)
    }

    override fun clearReference() {

    }
}
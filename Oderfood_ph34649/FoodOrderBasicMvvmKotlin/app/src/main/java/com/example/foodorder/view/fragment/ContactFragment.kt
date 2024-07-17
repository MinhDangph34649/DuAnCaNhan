package com.example.foodorder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentContactBinding
import com.example.foodorder.view.BaseFragment
import com.example.foodorder.view.activity.MainActivity
import com.example.foodorder.viewmodel.ContactViewModel

class ContactFragment : BaseFragment() {

    private var mContactViewModel: ContactViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false)
        mContactViewModel = ContactViewModel(activity)
        fragmentContactBinding.contactViewModel = mContactViewModel
        return fragmentContactBinding.root
    }

    override fun initToolbar() {
        val mainActivity = activity as MainActivity?
        mainActivity?.setToolBar(true, getString(R.string.contact))
    }

    override fun onDestroy() {
        super.onDestroy()
        mContactViewModel?.release()
    }
}
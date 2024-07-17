package com.example.foodorder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentHomeBinding
import com.example.foodorder.view.BaseFragment
import com.example.foodorder.view.activity.MainActivity
import com.example.foodorder.viewmodel.HomeViewModel

class HomeFragment : BaseFragment() {

    private var mHomeViewModel: HomeViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        mHomeViewModel = HomeViewModel(activity)
        fragmentHomeBinding.homeViewModel = mHomeViewModel
        return fragmentHomeBinding.root
    }

    override fun initToolbar() {
        val mainActivity = activity as MainActivity?
        mainActivity?.setToolBar(false, getString(R.string.home))
    }

    override fun onDestroy() {
        super.onDestroy()
        mHomeViewModel?.release()
    }
}
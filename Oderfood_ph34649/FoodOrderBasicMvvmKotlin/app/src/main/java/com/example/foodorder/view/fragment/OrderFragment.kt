package com.example.foodorder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentOrderBinding
import com.example.foodorder.view.BaseFragment
import com.example.foodorder.view.activity.MainActivity
import com.example.foodorder.viewmodel.OrderViewModel

class OrderFragment : BaseFragment() {

    private var mOrderViewModel: OrderViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false)
        mOrderViewModel = OrderViewModel(activity)
        fragmentOrderBinding.orderViewModel = mOrderViewModel
        return fragmentOrderBinding.root
    }

    override fun initToolbar() {
        val mainActivity = activity as MainActivity?
        mainActivity?.setToolBar(true, getString(R.string.order))
    }

    override fun onDestroy() {
        super.onDestroy()
        mOrderViewModel?.release()
    }
}
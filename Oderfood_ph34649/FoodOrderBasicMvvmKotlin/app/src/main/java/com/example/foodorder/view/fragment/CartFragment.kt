package com.example.foodorder.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodorder.R
import com.example.foodorder.databinding.FragmentCartBinding
import com.example.foodorder.event.ReloadListCartEvent
import com.example.foodorder.view.BaseFragment
import com.example.foodorder.view.activity.MainActivity
import com.example.foodorder.viewmodel.CartViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CartFragment : BaseFragment() {

    private var mCartViewModel: CartViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        mCartViewModel = CartViewModel(activity)
        fragmentCartBinding.cartViewModel = mCartViewModel
        return fragmentCartBinding.root
    }

    override fun initToolbar() {
        val mainActivity = activity as MainActivity?
        mainActivity?.setToolBar(true, getString(R.string.cart))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ReloadListCartEvent?) {
        mCartViewModel?.loadListFoodInCart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mCartViewModel?.release()
    }
}
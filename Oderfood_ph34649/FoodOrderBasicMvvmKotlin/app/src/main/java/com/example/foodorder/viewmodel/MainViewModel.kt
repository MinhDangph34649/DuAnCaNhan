package com.example.foodorder.viewmodel

import android.view.MenuItem
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.foodorder.R
import com.example.foodorder.adapter.MainViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainViewModel {

    var isShowToolbar: ObservableBoolean? = ObservableBoolean()
    var title: ObservableField<String>? = ObservableField()

    companion object {
        @BindingAdapter("item_selected")
        @JvmStatic
        fun setOnNavigationItemSelectedListener(bottomNavigation: BottomNavigationView, viewPager2: ViewPager2) {
            viewPager2.isUserInputEnabled = false
            val mainViewPagerAdapter = MainViewPagerAdapter(viewPager2.context as FragmentActivity)
            viewPager2.adapter = mainViewPagerAdapter
            bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem? ->
                val id = item?.itemId
                if (id == R.id.nav_home) {
                    viewPager2.currentItem = 0
                } else if (id == R.id.nav_cart) {
                    viewPager2.currentItem = 1
                } else if (id == R.id.nav_feedback) {
                    viewPager2.currentItem = 2
                } else if (id == R.id.nav_contact) {
                    viewPager2.currentItem = 3
                } else if (id == R.id.nav_order) {
                    viewPager2.currentItem = 4
                }
                true
            }
        }
    }
}
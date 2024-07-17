package com.example.foodorder.viewmodel

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.example.foodorder.constant.Constant
import com.example.foodorder.database.FoodDatabase
import com.example.foodorder.listener.IAddToCartSuccessListener
import com.example.foodorder.model.Food
import com.example.foodorder.utils.GlideUtils
import com.google.android.material.bottomsheet.BottomSheetDialog

class DialogCartViewModel(private var mActivity: Activity?, private var mBottomSheetDialog: BottomSheetDialog?,
                          var mFood: Food, private val iAddToCartSuccessListener: IAddToCartSuccessListener?) {

    var strTotalPrice: ObservableField<String>? = ObservableField()

    private fun initData() {
        val totalPrice = mFood.getRealPrice()
        strTotalPrice?.set(totalPrice.toString() + Constant.CURRENCY)
        mFood.setCount(1)
        mFood.setTotalPrice(totalPrice)
    }

    fun onClickSubtractCount(tvCount: TextView) {
        val count = tvCount.text.toString().toInt()
        if (count <= 1) {
            return
        }
        val newCount = tvCount.text.toString().toInt() - 1
        tvCount.text = newCount.toString()
        val totalPrice1 = mFood.getRealPrice() * newCount
        val strTotalPrice1 = totalPrice1.toString() + Constant.CURRENCY
        strTotalPrice?.set(strTotalPrice1)
        mFood.setCount(newCount)
        mFood.setTotalPrice(totalPrice1)
    }

    fun onClickAddCount(tvCount: TextView) {
        val newCount = tvCount.text.toString().toInt() + 1
        tvCount.text = newCount.toString()
        val totalPrice2 = mFood.getRealPrice() * newCount
        val strTotalPrice2 = totalPrice2.toString() + Constant.CURRENCY
        strTotalPrice?.set(strTotalPrice2)
        mFood.setCount(newCount)
        mFood.setTotalPrice(totalPrice2)
    }

    fun onClickCancel() {
        mBottomSheetDialog?.dismiss()
    }

    fun onClickAddToCart() {
        FoodDatabase.getInstance(mActivity!!)?.foodDAO()?.insertFood(mFood)
        iAddToCartSuccessListener?.addToCartSuccess()
    }

    fun release() {
        mActivity = null
        mBottomSheetDialog = null
    }

    companion object {
        @BindingAdapter("url_image")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView?, url: String?) {
            GlideUtils.loadUrl(url, imageView)
        }
    }

    init {
        initData()
    }
}
package com.example.foodorder.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.adapter.CartAdapter
import com.example.foodorder.constant.Constant
import com.example.foodorder.constant.GlobalFuntion
import com.example.foodorder.database.FoodDatabase
import com.example.foodorder.databinding.LayoutBottomSheetOrderBinding
import com.example.foodorder.listener.ICalculatePriceListener
import com.example.foodorder.listener.ISendOrderSuccessListener
import com.example.foodorder.model.Food
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class CartViewModel(private var mContext: Context?) {

    var listFoodInCart: ObservableList<Food>? = ObservableArrayList()
    private var mDialogOrderViewModel: DialogOrderViewModel? = null

    fun loadListFoodInCart() {
        if (listFoodInCart != null) {
            listFoodInCart!!.clear()
        } else {
            listFoodInCart = ObservableArrayList()
        }
        val list: MutableList<Food>? = FoodDatabase.getInstance(mContext!!)?.foodDAO()?.getListFoodCart()
        if (list != null) {
            listFoodInCart!!.addAll(list)
        }
    }

    fun onClickOrderCart() {
        if (mContext == null || listFoodInCart == null || listFoodInCart!!.isEmpty()) {
            return
        }
        val binding: LayoutBottomSheetOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.layout_bottom_sheet_order, null, false)
        val bottomSheetDialog = BottomSheetDialog(mContext!!)
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        mDialogOrderViewModel = DialogOrderViewModel(mContext, bottomSheetDialog, listFoodInCart,
                strTotalPrice, mAmount, object : ISendOrderSuccessListener{
            override fun sendOrderSuccess() {
                GlobalFuntion.showToastMessage(mContext, mContext!!.getString(R.string.msg_order_success))
                GlobalFuntion.hideSoftKeyboard(mContext as Activity?)
                bottomSheetDialog.dismiss()
                FoodDatabase.getInstance(mContext!!)?.foodDAO()?.deleteAllFood()
                clearCart()
            }
        })
        binding.dialogOrderViewModel = mDialogOrderViewModel
        bottomSheetDialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearCart() {
        listFoodInCart?.clear()
        mCartAdapter?.notifyDataSetChanged()
    }

    fun release() {
        mContext = null
        mDialogOrderViewModel?.release()
    }

    companion object {
        var strTotalPrice: String? = null
        private var mAmount = 0
        private var mCartAdapter: CartAdapter? = null
        @BindingAdapter("list_cart", "calculate_price")
        @JvmStatic
        fun loadListFoodInCart(recyclerView: RecyclerView, list: ObservableList<Food>?, textView: TextView) {
            val linearLayoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.layoutManager = linearLayoutManager
            val itemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
            recyclerView.addItemDecoration(itemDecoration)
            mCartAdapter = CartAdapter(list, object : ICalculatePriceListener{
                override fun calculatePrice(totalPrice: String?, amount: Int) {
                    textView.text = totalPrice
                    strTotalPrice = totalPrice
                    mAmount = amount
                }
            })
            strTotalPrice = getValueTotalPrice(recyclerView.context)
            textView.text = strTotalPrice
            recyclerView.adapter = mCartAdapter
        }

        private fun getValueTotalPrice(context: Context): String {
            val listFoodCart: MutableList<Food>? = FoodDatabase.getInstance(context)?.foodDAO()?.getListFoodCart()
            if (listFoodCart == null || listFoodCart.isEmpty()) {
                mAmount = 0
                return 0.toString() + Constant.CURRENCY
            }
            var totalPrice = 0
            for (food in listFoodCart) {
                totalPrice += food.getTotalPrice()
            }
            mAmount = totalPrice
            return totalPrice.toString() + Constant.CURRENCY
        }
    }

    init {
        loadListFoodInCart()
    }
}
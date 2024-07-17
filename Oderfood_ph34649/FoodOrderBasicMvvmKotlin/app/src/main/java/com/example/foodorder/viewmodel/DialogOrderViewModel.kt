package com.example.foodorder.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import com.example.foodorder.ControllerApplication
import com.example.foodorder.R
import com.example.foodorder.constant.Constant
import com.example.foodorder.constant.GlobalFuntion
import com.example.foodorder.listener.ISendOrderSuccessListener
import com.example.foodorder.model.Food
import com.example.foodorder.model.Order
import com.example.foodorder.utils.StringUtil
import com.example.foodorder.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class DialogOrderViewModel(private var mContext: Context?, private var mBottomSheetDialog: BottomSheetDialog?,
                           private val listFoodInCart: ObservableList<Food>?, var strTotalPrice: String?,
                           private val mAmount: Int, private val iSendOrderSuccessListener: ISendOrderSuccessListener?) {

    var strName: ObservableField<String?>? = ObservableField()
    var strAddress: ObservableField<String?>? = ObservableField()
    var strPhone: ObservableField<String?>? = ObservableField()

    fun release() {
        mContext = null
        mBottomSheetDialog = null
    }

    fun getStringListFoodsOrder(): String {
        if (listFoodInCart == null || listFoodInCart.isEmpty()) {
            return ""
        }
        var result = ""
        for (food in listFoodInCart) {
            result = if (StringUtil.isEmpty(result)) {
                ("- " + food.getName() + " (" + food.getRealPrice() + Constant.CURRENCY + ") "
                        + "- " + mContext?.getString(R.string.quantity) + " " + food.getCount())
            } else {
                (result + "\n" + ("- " + food.getName() + " (" + food.getRealPrice() + Constant.CURRENCY + ") "
                        + "- " + mContext?.getString(R.string.quantity) + " " + food.getCount()))

            }
        }
        return result
    }

    fun onClickCancel() {
        mBottomSheetDialog?.dismiss()
    }

    fun onClickSendOrder() {
        val name = strName?.get()
        val phone = strPhone?.get()
        val address = strAddress?.get()
        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(phone) || StringUtil.isEmpty(address)) {
            GlobalFuntion.showToastMessage(mContext, mContext?.getString(R.string.message_enter_infor_order))
        } else {
            val id = System.currentTimeMillis()
            val order = Order(id, name, phone, address,
                    mAmount, getStringListFoodsOrder(), Constant.TYPE_PAYMENT_CASH)
            ControllerApplication[mContext].getBookingDatabaseReference()
                    ?.child(Utils.getDeviceId(mContext))
                    ?.child(id.toString())
                    ?.setValue(order) { _: DatabaseError?, _: DatabaseReference? -> iSendOrderSuccessListener?.sendOrderSuccess() }
        }
    }
}
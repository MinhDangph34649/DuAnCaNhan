package com.example.foodorder.viewmodel

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.ControllerApplication
import com.example.foodorder.R
import com.example.foodorder.adapter.OrderAdapter
import com.example.foodorder.constant.GlobalFuntion
import com.example.foodorder.model.Order
import com.example.foodorder.utils.Utils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class OrderViewModel(private var mContext: Context?) {

    var listOrder: ObservableList<Order>? = ObservableArrayList()

    private fun getListOrders() {
        if (mContext == null) {
            return
        }
        ControllerApplication[mContext].getBookingDatabaseReference()?.child(Utils.getDeviceId(mContext))
                ?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (listOrder != null) {
                            listOrder!!.clear()
                        } else {
                            listOrder = ObservableArrayList()
                        }
                        for (dataSnapshot in snapshot.children) {
                            val order: Order? = dataSnapshot.getValue<Order>(Order::class.java)
                            listOrder?.add(0, order)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        listOrder = null
                    }
                })
    }

    fun release() {
        mContext = null
    }

    companion object {
        @BindingAdapter("list_data")
        @JvmStatic
        fun loadListOrder(recyclerView: RecyclerView, list: ObservableList<Order?>?) {
            if (list == null) {
                GlobalFuntion.showToastMessage(recyclerView.context,
                        recyclerView.context.getString(R.string.msg_get_date_error))
                return
            }
            val linearLayoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.layoutManager = linearLayoutManager
            val orderAdapter = OrderAdapter(list)
            recyclerView.adapter = orderAdapter
        }
    }

    init {
        getListOrders()
    }
}
package com.example.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.adapter.OrderAdapter.OrderViewHolder
import com.example.foodorder.databinding.ItemOrderBinding
import com.example.foodorder.model.Order

class OrderAdapter(private val mListOrder: MutableList<Order?>?) : RecyclerView.Adapter<OrderViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val itemOrderBinding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return OrderViewHolder(itemOrderBinding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = mListOrder?.get(position) ?: return
        holder.mItemOrderBinding.orderModel = order
    }

    override fun getItemCount(): Int {
        return mListOrder?.size ?: 0
    }

    class OrderViewHolder(itemOrderBinding: ItemOrderBinding) : RecyclerView.ViewHolder(itemOrderBinding.root) {
        val mItemOrderBinding: ItemOrderBinding = itemOrderBinding
    }
}
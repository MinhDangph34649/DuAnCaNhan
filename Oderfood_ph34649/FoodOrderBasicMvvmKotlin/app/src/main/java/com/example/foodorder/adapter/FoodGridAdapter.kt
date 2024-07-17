package com.example.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.adapter.FoodGridAdapter.FoodGridViewHolder
import com.example.foodorder.databinding.ItemFoodGridBinding
import com.example.foodorder.model.Food

class FoodGridAdapter(private val mListFoods: MutableList<Food?>?) : RecyclerView.Adapter<FoodGridViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodGridViewHolder {
        val itemFoodGridBinding = ItemFoodGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodGridViewHolder(itemFoodGridBinding)
    }

    override fun onBindViewHolder(holder: FoodGridViewHolder, position: Int) {
        val food = mListFoods?.get(position) ?: return
        holder.mItemFoodGridBinding.foodModel = food
    }

    override fun getItemCount(): Int {
        return mListFoods?.size ?: 0
    }

    class FoodGridViewHolder(val mItemFoodGridBinding: ItemFoodGridBinding) : RecyclerView.ViewHolder(mItemFoodGridBinding.root)
}
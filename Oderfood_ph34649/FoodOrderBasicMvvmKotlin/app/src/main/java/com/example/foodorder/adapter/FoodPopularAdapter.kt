package com.example.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.adapter.FoodPopularAdapter.FoodPopularViewHolder
import com.example.foodorder.databinding.ItemFoodPopularBinding
import com.example.foodorder.model.Food

class FoodPopularAdapter(private val mListFoods: MutableList<Food>?) : RecyclerView.Adapter<FoodPopularViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodPopularViewHolder {
        val itemFoodPopularBinding = ItemFoodPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodPopularViewHolder(itemFoodPopularBinding)
    }

    override fun onBindViewHolder(holder: FoodPopularViewHolder, position: Int) {
        val food = mListFoods?.get(position) ?: return
        holder.mItemFoodPopularBinding.foodModel = food
    }

    override fun getItemCount(): Int {
        return mListFoods?.size ?: 0
    }

    class FoodPopularViewHolder(itemFoodPopularBinding: ItemFoodPopularBinding) : RecyclerView.ViewHolder(itemFoodPopularBinding.root) {
        val mItemFoodPopularBinding: ItemFoodPopularBinding = itemFoodPopularBinding
    }
}
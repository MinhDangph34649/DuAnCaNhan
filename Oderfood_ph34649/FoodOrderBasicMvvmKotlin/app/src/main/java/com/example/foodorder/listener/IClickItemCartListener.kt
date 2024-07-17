package com.example.foodorder.listener

import android.content.Context
import com.example.foodorder.model.Food

interface IClickItemCartListener {
    fun clickDeteteFood(context: Context, food: Food?, position: Int)
    fun updateItemFood(context: Context, food: Food?, position: Int)
}
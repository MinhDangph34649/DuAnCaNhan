package com.example.foodorder.listener

interface ICalculatePriceListener {
    fun calculatePrice(totalPrice: String?, amount: Int)
}
package com.example.foodorder.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.adapter.CartAdapter.CartViewHolder
import com.example.foodorder.constant.Constant
import com.example.foodorder.database.FoodDatabase
import com.example.foodorder.databinding.ItemCartBinding
import com.example.foodorder.listener.ICalculatePriceListener
import com.example.foodorder.listener.IClickItemCartListener
import com.example.foodorder.model.Food

class CartAdapter(private val mListFoods: MutableList<Food>?,
                  private val iCalculatePriceListener: ICalculatePriceListener?) : RecyclerView.Adapter<CartViewHolder?>(), IClickItemCartListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemCartBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(itemCartBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val food = mListFoods?.get(position) ?: return
        food.setAdapterPosition(holder.adapterPosition)
        food.setClickItemCartListener(this)
        holder.mItemCartBinding.foodModel = food
    }

    override fun getItemCount(): Int {
        return mListFoods?.size ?: 0
    }

    override fun clickDeteteFood(context: Context, food: Food?, position: Int) {
        showConfirmDialogDeleteFood(context, food, position)
    }

    override fun updateItemFood(context: Context, food: Food?, position: Int) {
        FoodDatabase.getInstance(context)?.foodDAO()?.updateFood(food)
        notifyItemChanged(position)
        calculateTotalPrice(context)
    }

    private fun showConfirmDialogDeleteFood(context: Context, food: Food?, position: Int) {
        AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.confirm_delete_food))
                .setMessage(context.getString(R.string.message_delete_food))
                .setPositiveButton(context.getString(R.string.delete)) { _: DialogInterface?, _: Int ->
                    FoodDatabase.getInstance(context)?.foodDAO()?.deleteFood(food)
                    mListFoods?.removeAt(position)
                    notifyItemRemoved(position)
                    calculateTotalPrice(context)
                }
                .setNegativeButton(context.getString(R.string.dialog_cancel)) {
                    dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
                .show()
    }

    private fun calculateTotalPrice(context: Context) {
        val listFoodCart: MutableList<Food>? = FoodDatabase.getInstance(context)?.foodDAO()?.getListFoodCart()
        if (listFoodCart == null || listFoodCart.isEmpty()) {
            val strZero = 0.toString() + Constant.CURRENCY
            iCalculatePriceListener?.calculatePrice(strZero, 0)
            return
        }
        var totalPrice = 0
        for (food in listFoodCart) {
            totalPrice += food.getTotalPrice()
        }
        val totalString = totalPrice.toString() + Constant.CURRENCY
        iCalculatePriceListener?.calculatePrice(totalString, totalPrice)
    }

    class CartViewHolder(val mItemCartBinding: ItemCartBinding) : RecyclerView.ViewHolder(mItemCartBinding.root)
}
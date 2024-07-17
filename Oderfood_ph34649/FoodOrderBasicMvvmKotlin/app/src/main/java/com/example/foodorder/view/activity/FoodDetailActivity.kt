package com.example.foodorder.view.activity

import android.os.Bundle
import com.example.foodorder.constant.Constant
import com.example.foodorder.databinding.ActivityFoodDetailBinding
import com.example.foodorder.model.Food
import com.example.foodorder.view.BaseActivity
import com.example.foodorder.viewmodel.FoodDetailViewModel

class FoodDetailActivity : BaseActivity() {

    private var mFoodDetailViewModel: FoodDetailViewModel? = null
    private var mFood: Food? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityFoodDetailBinding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(activityFoodDetailBinding.root)
        getDataIntent()
        mFoodDetailViewModel = FoodDetailViewModel(this, mFood)
        activityFoodDetailBinding.foodDetailViewModel = mFoodDetailViewModel
    }

    private fun getDataIntent() {
        val bundle = intent.extras
        if (bundle != null) {
            mFood = bundle[Constant.KEY_INTENT_FOOD_OBJECT] as Food?
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mFoodDetailViewModel?.release()
    }
}
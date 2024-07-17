package com.example.foodorder.viewmodel

import android.app.Activity
import android.graphics.Paint
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.adapter.MoreImageAdapter
import com.example.foodorder.constant.Constant
import com.example.foodorder.database.FoodDatabase
import com.example.foodorder.databinding.LayoutBottomSheetCartBinding
import com.example.foodorder.event.ReloadListCartEvent
import com.example.foodorder.listener.IAddToCartSuccessListener
import com.example.foodorder.model.Food
import com.example.foodorder.model.Image
import com.example.foodorder.utils.GlideUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.greenrobot.eventbus.EventBus

class FoodDetailViewModel(private var mActivity: Activity?, var mFood: Food?) {

    private var mDialogCartViewModel: DialogCartViewModel? = null
    var isSale: ObservableBoolean = ObservableBoolean()
    var isMoreImages: ObservableBoolean = ObservableBoolean()
    var isFoodInCart: ObservableBoolean = ObservableBoolean()
    var listMoreImages: ObservableList<Image>? = ObservableArrayList()
    var strSale: String? = null
    private var strPriceOld: String? = null
    var strRealPrice: String? = null
    var strStatusCart: ObservableField<String> = ObservableField()

    private fun initData() {
        if (mFood == null || mActivity == null) {
            return
        }
        if (mFood!!.getSale() <= 0) {
            isSale.set(false)
            strRealPrice = mFood!!.getPrice().toString() + Constant.CURRENCY
        } else {
            isSale.set(true)
            strSale = mActivity?.getString(R.string.label_discount) + " " + mFood!!.getSale() + "%"
            strPriceOld = mFood!!.getPrice().toString() + Constant.CURRENCY
            strRealPrice = mFood!!.getRealPrice().toString() + Constant.CURRENCY
        }
        if (mFood!!.getImages() == null || mFood!!.getImages()!!.isEmpty()) {
            isMoreImages.set(false)
        } else {
            isMoreImages.set(true)
            listMoreImages?.addAll(mFood!!.getImages()!!)
        }
        if (isFoodInCart(mFood!!.getId())) {
            isFoodInCart.set(true)
            strStatusCart.set(mActivity!!.getString(R.string.added_to_cart))
        } else {
            isFoodInCart.set(false)
            strStatusCart.set(mActivity!!.getString(R.string.add_to_cart))
        }
    }

    fun getStrStatusCart(textView: TextView): ObservableField<String> {
        if (isFoodInCart.get()) {
            textView.setBackgroundResource(R.drawable.bg_gray_shape_corner_6)
            textView.setTextColor(ContextCompat.getColor(mActivity!!, R.color.textColorPrimary))
        } else {
            textView.setBackgroundResource(R.drawable.bg_green_shape_corner_6)
            textView.setTextColor(ContextCompat.getColor(mActivity!!, R.color.white))
        }
        return strStatusCart
    }

    fun getStrPriceOld(textView: TextView): String? {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        return strPriceOld
    }

    fun onClickButtonBack() {
        mActivity?.onBackPressed()
    }

    fun isFoodInCart(foodId: Int): Boolean {
        val list: MutableList<Food>? = FoodDatabase.getInstance(mActivity!!)?.foodDAO()?.checkFoodInCart(foodId)
        return list != null && list.isNotEmpty()
    }

    fun onClickAddToCart() {
        if (mActivity == null || mFood == null || isFoodInCart.get()) {
            return
        }
        val binding: LayoutBottomSheetCartBinding = DataBindingUtil.inflate(LayoutInflater.from(mActivity),
                R.layout.layout_bottom_sheet_cart, null, false)
        val bottomSheetDialog = BottomSheetDialog(mActivity!!)
        bottomSheetDialog.setContentView(binding.root)
        mDialogCartViewModel = DialogCartViewModel(mActivity, bottomSheetDialog, mFood!!, object : IAddToCartSuccessListener{
            override fun addToCartSuccess() {
                bottomSheetDialog.dismiss()
                isFoodInCart.set(true)
                strStatusCart.set(mActivity!!.getString(R.string.added_to_cart))
                EventBus.getDefault().post(ReloadListCartEvent())
            }
        })
        binding.dialogCartViewModel = mDialogCartViewModel
        bottomSheetDialog.show()
    }

    fun release() {
        mActivity = null
        mDialogCartViewModel?.release()
    }

    companion object {
        @BindingAdapter("url_image")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView?, url: String?) {
            GlideUtils.loadUrlBanner(url, imageView)
        }

        @BindingAdapter("list_more_image")
        @JvmStatic
        fun loadListMoreImages(recyclerView: RecyclerView, list: ObservableList<Image?>?) {
            val gridLayoutManager = GridLayoutManager(recyclerView.context, 2)
            recyclerView.layoutManager = gridLayoutManager
            val moreImageAdapter = MoreImageAdapter(list)
            recyclerView.adapter = moreImageAdapter
        }
    }

    init {
        initData()
    }
}
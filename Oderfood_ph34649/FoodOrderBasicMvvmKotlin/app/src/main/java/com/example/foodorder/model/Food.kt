package com.example.foodorder.model

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.foodorder.BR
import com.example.foodorder.R
import com.example.foodorder.constant.Constant
import com.example.foodorder.constant.GlobalFuntion
import com.example.foodorder.listener.IClickItemCartListener
import com.example.foodorder.utils.GlideUtils
import com.example.foodorder.view.activity.FoodDetailActivity
import java.io.Serializable

@Entity(tableName = "food")
class Food : BaseObservable(), Serializable {
    @PrimaryKey
    private var id = 0
    private var name: String? = null
    private var image: String? = null
    private var banner: String? = null
    private var description: String? = null
    private var price = 0
    private var sale = 0
    private var count = 0
    private var totalPrice = 0
    private var popular = false

    @Ignore
    private var images: MutableList<Image?>? = null

    @Ignore
    private var adapterPosition = 0

    @Ignore
    private var iClickItemCartListener: IClickItemCartListener? = null
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPrice(): Int {
        return price
    }

    fun setPrice(price: Int) {
        this.price = price
    }

    fun getRealPrice(): Int {
        return if (sale <= 0) {
            price
        } else price - price * sale / 100
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getBanner(): String? {
        return banner
    }

    fun setBanner(banner: String?) {
        this.banner = banner
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getSale(): Int {
        return sale
    }

    fun setSale(sale: Int) {
        this.sale = sale
    }

    @Bindable
    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
        notifyPropertyChanged(BR.count)
    }

    fun getTotalPrice(): Int {
        return totalPrice
    }

    fun setTotalPrice(totalPrice: Int) {
        this.totalPrice = totalPrice
    }

    fun isPopular(): Boolean {
        return popular
    }

    fun setPopular(popular: Boolean) {
        this.popular = popular
    }

    fun getImages(): MutableList<Image?>? {
        return images
    }

    fun setImages(images: MutableList<Image?>?) {
        this.images = images
    }

    fun getAdapterPosition(): Int {
        return adapterPosition
    }

    fun setAdapterPosition(adapterPosition: Int) {
        this.adapterPosition = adapterPosition
    }

    fun getClickItemCartListener(): IClickItemCartListener? {
        return iClickItemCartListener
    }

    fun setClickItemCartListener(iClickItemCartListener: IClickItemCartListener?) {
        this.iClickItemCartListener = iClickItemCartListener
    }

    fun getStringSale(textView: TextView?): String {
        return textView?.context?.getString(R.string.label_discount) + " " + getSale() + "%"
    }

    fun isSaleOff(): Boolean {
        return getSale() > 0
    }

    fun getStringOldPrice(textview: TextView): String {
        textview.paintFlags = textview.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        return getPrice().toString() + Constant.CURRENCY
    }

    fun getStringRealPrice(): String {
        return if (isSaleOff()) {
            getRealPrice().toString() + Constant.CURRENCY
        } else {
            getPrice().toString() + Constant.CURRENCY
        }
    }

    fun getStringCount(): String {
        return getCount().toString()
    }

    fun goToFoodDetail(view: View?) {
        val bundle = Bundle()
        bundle.putSerializable(Constant.KEY_INTENT_FOOD_OBJECT, this)
        GlobalFuntion.startActivity(view?.context, FoodDetailActivity::class.java, bundle)
    }

    fun onClickButtonSubtract(view: View) {
        val count = getCount()
        if (count <= 1) {
            return
        }
        val newCount = count - 1
        val totalPrice = getRealPrice() * newCount
        setCount(newCount)
        setTotalPrice(totalPrice)
        iClickItemCartListener?.updateItemFood(view.context, this, getAdapterPosition())
    }

    fun onClickButtonAdd(view: View) {
        val newCount = getCount() + 1
        val totalPrice = getRealPrice() * newCount
        setCount(newCount)
        setTotalPrice(totalPrice)
        iClickItemCartListener?.updateItemFood(view.context, this, getAdapterPosition())
    }

    fun onClickButtonDelete(view: View) {
        iClickItemCartListener?.clickDeteteFood(view.context, this, getAdapterPosition())
    }

    companion object {
        @BindingAdapter("banner_image")
        @JvmStatic
        fun loadImageBannerFromUrl(imageView: ImageView?, url: String?) {
            GlideUtils.loadUrlBanner(url, imageView)
        }

        @BindingAdapter("normal_image")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView?, url: String?) {
            GlideUtils.loadUrl(url, imageView)
        }
    }
}
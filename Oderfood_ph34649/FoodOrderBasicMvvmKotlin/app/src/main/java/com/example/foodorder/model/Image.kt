package com.example.foodorder.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.foodorder.utils.GlideUtils
import java.io.Serializable

class Image : Serializable {
    private var url: String? = null

    constructor() {}
    constructor(url: String?) {
        this.url = url
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String?) {
        this.url = url
    }

    companion object {
        @BindingAdapter("url_image")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView?, url: String?) {
            GlideUtils.loadUrl(url, imageView)
        }
    }
}
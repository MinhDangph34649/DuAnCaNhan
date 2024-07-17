package com.example.foodorder.model

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.foodorder.constant.GlobalFuntion

class Contact(private var id: Int, private var image: Int, private var name: String?) {

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun clickContactItem(view: View?) {
        val context = view?.context
        when (id) {
            FACEBOOK -> GlobalFuntion.onClickOpenFacebook(context)
            HOTLINE -> GlobalFuntion.callPhoneNumber(context as Activity)
            GMAIL -> GlobalFuntion.onClickOpenGmail(context)
            SKYPE -> GlobalFuntion.onClickOpenSkype(context)
            YOUTUBE -> GlobalFuntion.onClickOpenYoutubeChannel(context)
            ZALO -> GlobalFuntion.onClickOpenZalo(context)
        }
    }

    companion object {
        const val FACEBOOK = 0
        const val HOTLINE = 1
        const val GMAIL = 2
        const val SKYPE = 3
        const val YOUTUBE = 4
        const val ZALO = 5
        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageViewResource(imageView: ImageView?, resource: Int) {
            imageView?.setImageResource(resource)
        }
    }
}
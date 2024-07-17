package com.example.foodorder.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.R
import com.example.foodorder.adapter.ContactAdapter
import com.example.foodorder.constant.AboutUsConfig
import com.example.foodorder.model.Contact

class ContactViewModel(private var mContext: Context?) {

    var listContacts: ObservableList<Contact>? = ObservableArrayList()
    var aboutUsTitle = ObservableField<String>()
    var aboutUsContent = ObservableField<String>()
    var aboutUsWebsite = ObservableField<String>()

    private fun getListContacts() {
        listContacts?.add(Contact(Contact.FACEBOOK, R.drawable.ic_facebook, mContext?.getString(R.string.label_facebook)))
        listContacts?.add(Contact(Contact.HOTLINE, R.drawable.ic_hotline, mContext?.getString(R.string.label_call)))
        listContacts?.add(Contact(Contact.GMAIL, R.drawable.ic_gmail, mContext?.getString(R.string.label_gmail)))
        listContacts?.add(Contact(Contact.SKYPE, R.drawable.ic_skype, mContext?.getString(R.string.label_skype)))
        listContacts?.add(Contact(Contact.YOUTUBE, R.drawable.ic_youtube, mContext?.getString(R.string.label_youtube)))
        listContacts?.add(Contact(Contact.ZALO, R.drawable.ic_zalo, mContext?.getString(R.string.label_zalo)))
    }

    fun release() {
        mContext = null
    }

    companion object {
        @BindingAdapter("list_data")
        @JvmStatic
        fun loadListContacts(recyclerView: RecyclerView, list: ObservableList<Contact>?) {
            val layoutManager = GridLayoutManager(recyclerView.context, 3)
            recyclerView.layoutManager = layoutManager
            val contactAdapter = ContactAdapter(list)
            recyclerView.adapter = contactAdapter
        }
    }

    fun onClickWebsite() {
        mContext?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AboutUsConfig.WEBSITE)))
    }

    init {
        aboutUsTitle.set(AboutUsConfig.ABOUT_US_TITLE)
        aboutUsContent.set(AboutUsConfig.ABOUT_US_CONTENT)
        aboutUsWebsite.set(AboutUsConfig.ABOUT_US_WEBSITE_TITLE)
        getListContacts()
    }
}
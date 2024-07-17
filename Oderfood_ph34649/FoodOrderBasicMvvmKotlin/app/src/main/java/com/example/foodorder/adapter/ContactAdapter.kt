package com.example.foodorder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorder.adapter.ContactAdapter.ContactViewHolder
import com.example.foodorder.databinding.ItemContactBinding
import com.example.foodorder.model.Contact

class ContactAdapter(private val listContact: MutableList<Contact>?) : RecyclerView.Adapter<ContactViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemContactBinding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(itemContactBinding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = listContact?.get(position)
        if (contact != null) {
            holder.mItemContactBinding.contactModel = contact
        }
    }

    override fun getItemCount(): Int {
        return listContact?.size ?: 0
    }

    class ContactViewHolder(val mItemContactBinding: ItemContactBinding) : RecyclerView.ViewHolder(mItemContactBinding.root)
}
package com.example.foodorder.model

import android.app.Activity
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.foodorder.BR
import com.example.foodorder.ControllerApplication
import com.example.foodorder.R
import com.example.foodorder.constant.GlobalFuntion
import com.example.foodorder.utils.StringUtil
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class Feedback : BaseObservable {

    private var name: String? = null
    private var phone: String? = null
    private var email: String? = null
    private var comment: String? = null

    constructor() {}
    constructor(name: String?, phone: String?, email: String?, comment: String?) {
        this.name = name
        this.phone = phone
        this.email = email
        this.comment = comment
    }

    @Bindable
    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
        notifyPropertyChanged(BR.name)
    }

    @Bindable
    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String?) {
        this.phone = phone
        notifyPropertyChanged(BR.phone)
    }

    @Bindable
    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
        notifyPropertyChanged(BR.email)
    }

    @Bindable
    fun getComment(): String? {
        return comment
    }

    fun setComment(comment: String?) {
        this.comment = comment
        notifyPropertyChanged(BR.comment)
    }

    fun clickSendFeedback(view: View?) {
        val context = view?.context
        if (StringUtil.isEmpty(name)) {
            GlobalFuntion.showToastMessage(context, context?.getString(R.string.name_require))
        } else if (StringUtil.isEmpty(comment)) {
            GlobalFuntion.showToastMessage(context, context?.getString(R.string.comment_require))
        } else {
            val feedback = Feedback(name, phone, email, comment)
            val id = System.currentTimeMillis()
            ControllerApplication[context].getFeedbackDatabaseReference()
                    ?.child(id.toString())
                    ?.setValue(feedback) { _: DatabaseError?, _: DatabaseReference? ->
                        GlobalFuntion.hideSoftKeyboard(context as Activity)
                        GlobalFuntion.showToastMessage(context, context.getString(R.string.send_feedback_success))
                        setName("")
                        setPhone("")
                        setEmail("")
                        setComment("")
                    }
        }
    }
}
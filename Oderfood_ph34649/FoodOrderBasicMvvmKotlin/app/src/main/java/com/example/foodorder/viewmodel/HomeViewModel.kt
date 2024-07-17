package com.example.foodorder.viewmodel

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.foodorder.ControllerApplication
import com.example.foodorder.R
import com.example.foodorder.adapter.FoodGridAdapter
import com.example.foodorder.adapter.FoodPopularAdapter
import com.example.foodorder.constant.GlobalFuntion
import com.example.foodorder.model.Food
import com.example.foodorder.utils.StringUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import me.relex.circleindicator.CircleIndicator3
import java.util.*

class HomeViewModel(private var mContext: Context?) : BaseObservable() {

    var listFood: ObservableList<Food>? = ObservableArrayList()
    var listFoodPopular: ObservableList<Food>? = ObservableArrayList()
    var isSuccess: ObservableBoolean = ObservableBoolean()
    private var stringHint: ObservableField<String>? = ObservableField()

    private fun getListFoodFromFirebase(key: String?) {
        if (mContext == null) {
            return
        }
        ControllerApplication[mContext].getFoodDatabaseReference()
                ?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (listFood != null) {
                            listFood!!.clear()
                        } else {
                            listFood = ObservableArrayList()
                        }
                        for (dataSnapshot in snapshot.children) {
                            val food: Food = dataSnapshot.getValue<Food>(Food::class.java) ?: return
                            if (StringUtil.isEmpty(key)) {
                                listFood?.add(0, food)
                            } else {
                                if (GlobalFuntion.getTextSearch(food.getName())!!.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
                                                .contains(GlobalFuntion.getTextSearch(key)!!.toLowerCase(Locale.getDefault()).trim { it <= ' ' })) {
                                    listFood?.add(0, food)
                                }
                            }
                        }
                        getListFoodPopular(listFood)
                        isSuccess.set(true)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        listFood = null
                    }
                })
    }

    private fun getListFoodPopular(listFood: MutableList<Food?>?) {
        if (listFoodPopular != null) {
            listFoodPopular!!.clear()
        } else {
            listFoodPopular = ObservableArrayList()
        }
        if (listFood == null || listFood.isEmpty()) {
            return
        }
        for (food in listFood) {
            if (food!!.isPopular()) {
                listFoodPopular!!.add(food)
            }
        }
    }

    fun getStringHint(editText: EditText): ObservableField<String>? {
        stringHint?.set(mContext?.getString(R.string.hint_search_name))
        editText.setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = editText.text.toString()
                searchFood(keyword)
                return@setOnEditorActionListener true
            }
            false
        }
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val strKey = s.toString().trim { it <= ' ' }
                if (StringUtil.isEmpty(strKey)) {
                    searchFood("")
                }
            }
        })
        return stringHint
    }

    fun onClickButtonSearch(editText: EditText) {
        val keyword = editText.text.toString()
        searchFood(keyword)
    }

    private fun searchFood(key: String?) {
        if (listFood != null) {
            listFood!!.clear()
        }
        getListFoodFromFirebase(key)
    }

    fun release() {
        mContext = null
    }

    companion object {
        @BindingAdapter("list_data")
        @JvmStatic
        fun loadListFood(recyclerView: RecyclerView, list: ObservableList<Food>?) {
            GlobalFuntion.hideSoftKeyboard(recyclerView.context as Activity)
            if (list == null) {
                GlobalFuntion.showToastMessage(recyclerView.context,
                        recyclerView.context.getString(R.string.msg_get_date_error))
                return
            }
            val gridLayoutManager = GridLayoutManager(recyclerView.context, 2)
            recyclerView.layoutManager = gridLayoutManager
            val foodGridAdapter = FoodGridAdapter(list)
            recyclerView.adapter = foodGridAdapter
        }

        @BindingAdapter(value = ["list_data_popular", "indicator_viewpager"])
        @JvmStatic
        fun loadListFoodPopular(viewPager2: ViewPager2, list: ObservableList<Food>?, indicator3: CircleIndicator3) {
            val foodPopularAdapter = FoodPopularAdapter(list)
            viewPager2.adapter = foodPopularAdapter
            val handlerBanner = Handler(Looper.getMainLooper())
            val runnableBanner = Runnable label@{
                if (list == null || list.isEmpty()) {
                    return@label
                }
                if (viewPager2.currentItem == list.size - 1) {
                    viewPager2.currentItem = 0
                    return@label
                }
                viewPager2.currentItem = viewPager2.currentItem + 1
            }
            viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handlerBanner.removeCallbacks(runnableBanner)
                    handlerBanner.postDelayed(runnableBanner, 3000)
                }
            })
            indicator3.setViewPager(viewPager2)
        }
    }

    init {
        getListFoodFromFirebase("")
    }
}
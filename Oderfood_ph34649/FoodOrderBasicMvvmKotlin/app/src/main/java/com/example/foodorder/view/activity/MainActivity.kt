package com.example.foodorder.view.activity

import android.os.Bundle
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.example.foodorder.R
import com.example.foodorder.databinding.ActivityMainBinding
import com.example.foodorder.view.BaseActivity
import com.example.foodorder.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private var mMainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        mMainViewModel = MainViewModel()
        activityMainBinding.mainViewModel = mMainViewModel
        setContentView(activityMainBinding.root)
    }

    fun setToolBar(isShow: Boolean, title: String?) {
        mMainViewModel?.isShowToolbar?.set(isShow)
        if (isShow) {
            mMainViewModel?.title?.set(title)
        }
    }

    override fun onBackPressed() {
        showConfirmExitApp()
    }

    private fun showConfirmExitApp() {
        MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.msg_exit_app))
                .positiveText(getString(R.string.action_ok))
                .onPositive { _: MaterialDialog?, _: DialogAction? -> finish() }
                .negativeText(getString(R.string.action_cancel))
                .cancelable(false)
                .show()
    }
}
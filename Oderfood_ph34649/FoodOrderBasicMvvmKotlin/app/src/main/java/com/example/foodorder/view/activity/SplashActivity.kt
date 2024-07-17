package com.example.foodorder.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.foodorder.constant.GlobalFuntion
import com.example.foodorder.databinding.ActivitySplashBinding
import com.example.foodorder.view.BaseActivity
import com.example.foodorder.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activitySplashBinding: ActivitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        activitySplashBinding.splashViewModel = SplashViewModel()
        setContentView(activitySplashBinding.root)

        startMainActivity()
    }

    private fun startMainActivity() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            GlobalFuntion.startActivity(this@SplashActivity, MainActivity::class.java)
            finish()
        }, 2000)
    }
}
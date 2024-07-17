package com.example.foodorder.viewmodel

import androidx.databinding.ObservableField
import com.example.foodorder.constant.AboutUsConfig

class SplashViewModel {
    var aboutUsTitle = ObservableField<String>()
    var aboutUsSlogan = ObservableField<String>()

    init {
        aboutUsTitle.set(AboutUsConfig.ABOUT_US_TITLE)
        aboutUsSlogan.set(AboutUsConfig.ABOUT_US_SLOGAN)
    }
}
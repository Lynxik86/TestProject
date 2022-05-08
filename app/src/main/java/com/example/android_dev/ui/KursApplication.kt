package com.example.android_dev.ui

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KursApplication : Application(){
    override fun onCreate() {
        super.onCreate()

    }
}
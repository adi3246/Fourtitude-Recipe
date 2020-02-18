package com.fourtitude.asia.recipe

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication

class ApplicationClass : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        contextApp = applicationContext

    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var contextApp: Context

    }
}
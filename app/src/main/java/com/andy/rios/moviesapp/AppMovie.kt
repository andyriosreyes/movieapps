package com.andy.rios.moviesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppMovie : Application(){

    companion object {
        lateinit var app: AppMovie
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}
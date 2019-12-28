package com.balzhoyt.cafezh.muestras.utils

import android.app.Application

class PrefsApp : Application(){
    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}
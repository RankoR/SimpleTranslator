package com.g2pdev.simpletranslator

import android.app.Application
import com.g2pdev.simpletranslator.di.DiHolder

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DiHolder.init(this)
    }

}
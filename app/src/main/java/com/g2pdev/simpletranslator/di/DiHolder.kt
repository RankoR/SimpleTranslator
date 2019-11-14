package com.g2pdev.simpletranslator.di

import android.content.Context
import com.g2pdev.simpletranslator.di.component.AppComponent
import com.g2pdev.simpletranslator.di.component.DaggerAppComponent
import com.g2pdev.simpletranslator.di.module.AppModule

object DiHolder {

    lateinit var appComponent: AppComponent

    fun init(context: Context) {
        if (::appComponent.isInitialized) {
            throw IllegalStateException("AppComponent already initialized!")
        }

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context))
            .build()
    }

}
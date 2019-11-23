package com.g2pdev.simpletranslator

import android.app.Application
import com.facebook.stetho.Stetho
import com.g2pdev.simpletranslator.di.DiHolder
import timber.log.Timber
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var timberTree: Timber.Tree

    override fun onCreate() {
        super.onCreate()

        initDi()
        initLogging()
        initStetho()
    }

    private fun initDi() {
        DiHolder.init(this)
        DiHolder.appComponent.inject(this)
    }

    private fun initLogging() {
        Timber.plant(timberTree)
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

}
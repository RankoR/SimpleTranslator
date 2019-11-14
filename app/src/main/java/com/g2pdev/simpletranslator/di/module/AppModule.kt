package com.g2pdev.simpletranslator.di.module

import android.content.Context
import com.g2pdev.simpletranslator.BuildConfig
import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton

@Module
class AppModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideTimberTree(): Timber.Tree {
        if (BuildConfig.DEBUG) {
            return Timber.DebugTree()
        } else {
            throw IllegalStateException("Create tree for production")
        }
    }

}
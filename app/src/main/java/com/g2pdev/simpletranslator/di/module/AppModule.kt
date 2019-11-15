package com.g2pdev.simpletranslator.di.module

import android.content.Context
import android.content.res.Resources
import androidx.work.WorkManager
import com.g2pdev.simpletranslator.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    @Provides
    @Singleton
    fun provideResources(
        context: Context
    ): Resources = context.resources

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }

}
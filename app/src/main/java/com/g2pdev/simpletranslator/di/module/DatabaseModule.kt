package com.g2pdev.simpletranslator.di.module

import android.content.Context
import androidx.room.Room
import com.g2pdev.simpletranslator.db.TranslatorDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideTranslatorDatabase(
        context: Context
    ): TranslatorDatabase {
        return Room
            .databaseBuilder(
                context,
                TranslatorDatabase::class.java,
                translatorDbName
            )
            .build()
    }

    private companion object {
        private const val translatorDbName = "translator"
    }

}
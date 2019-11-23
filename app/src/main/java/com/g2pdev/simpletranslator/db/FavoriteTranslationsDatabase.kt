package com.g2pdev.simpletranslator.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FavoriteTranslation::class
    ],

    version = 1
)
abstract class FavoriteTranslationsDatabase : RoomDatabase() {

    abstract fun favoriteTranslationsDao(): FavoriteTranslationsDao
}
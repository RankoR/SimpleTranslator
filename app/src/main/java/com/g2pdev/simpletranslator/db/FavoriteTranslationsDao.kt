package com.g2pdev.simpletranslator.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteTranslationsDao {

    @Query("SELECT * FROM favorite_translations")
    fun getAll(): Single<List<FavoriteTranslation>>

    @Insert
    fun add(favoriteTranslation: FavoriteTranslation): Completable

    @Delete
    fun delete(favoriteTranslation: FavoriteTranslation): Completable

}
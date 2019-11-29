package com.g2pdev.translator.base.cache

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single

abstract class BaseCache<T>(
    private val gson: Gson,
    private val key: String,
    context: Context,
    name: String
) {

    private val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    protected abstract fun getType(): Class<T>

    protected fun getFromCache(): Single<T> {
        return Single.fromCallable {
            val json = sharedPreferences.getString(key, defaultValue)

            if (json == defaultValue) {
                throw NoValueException()
            }

            gson.fromJson(json, getType())
        }
    }

    protected fun putToCache(obj: T): Completable {
        return Completable.fromCallable {
            val json = gson.toJson(obj)

            sharedPreferences.edit {
                putString(key, json)
            }
        }
    }

    class NoValueException : Exception()

    private companion object {
        private const val defaultValue = ""
    }

}
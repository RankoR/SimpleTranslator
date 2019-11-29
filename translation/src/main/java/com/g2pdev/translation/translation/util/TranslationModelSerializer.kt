package com.g2pdev.translation.translation.util

import com.g2pdev.translation.translation.model.TranslationModel
import com.google.gson.Gson

interface TranslationModelSerializer {
    fun serialize(translationModel: TranslationModel): String
    fun deserialize(jsonString: String): TranslationModel
}

class TranslationModelSerializerImpl(
    private val gson: Gson
) : TranslationModelSerializer {

    override fun serialize(translationModel: TranslationModel): String {
        return gson.toJson(translationModel)
    }

    override fun deserialize(jsonString: String): TranslationModel {
        return gson.fromJson(jsonString, TranslationModel::class.java)
    }
}
package com.g2pdev.simpletranslator.translation.language

import android.os.Parcelable
import com.g2pdev.simpletranslator.translation.model.TranslationModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguagePair(
    val source: TranslationModel,
    val target: TranslationModel
) : Parcelable
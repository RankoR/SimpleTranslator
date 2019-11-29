package com.g2pdev.translation.translation.language

import android.os.Parcelable
import com.g2pdev.translation.translation.model.TranslationModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguagePair(
    val source: TranslationModel,
    val target: TranslationModel
) : Parcelable {

    fun copyWithReplacedSourceLanguage(newSource: TranslationModel): LanguagePair {
        return LanguagePair(
            newSource,
            this.target
        )
    }

    fun copyWithReplacedTargetLanguage(newTarget: TranslationModel): LanguagePair {
        return LanguagePair(
            this.source,
            newTarget
        )
    }

}
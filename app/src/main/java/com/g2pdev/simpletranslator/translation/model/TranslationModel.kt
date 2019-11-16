package com.g2pdev.simpletranslator.translation.model

import android.os.Parcelable
import com.g2pdev.simpletranslator.translation.language.Language
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TranslationModel(
    val language: Language,
    val name: String,
    var ordering: Int = 0
) : Comparable<TranslationModel>, Parcelable {

    @IgnoredOnParcel
    @delegate:Transient
    val isDeletable by lazy { language == Language.EN }

    override fun compareTo(other: TranslationModel): Int {
        return when {
            ordering != other.ordering -> this.ordering - other.ordering
            name != other.name -> name.compareTo(other.name)
            else -> 0
        }
    }
}
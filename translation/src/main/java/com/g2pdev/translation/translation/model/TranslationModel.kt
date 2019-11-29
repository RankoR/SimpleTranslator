package com.g2pdev.translation.translation.model

import android.os.Parcelable
import com.g2pdev.translation.translation.language.Language
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * TODO: Keywords for search in other languages
 */
@Parcelize
data class TranslationModel(
    val language: Language,
    val name: String,
    var ordering: Int = 0
) : Comparable<TranslationModel>, Parcelable {

    @IgnoredOnParcel
    val isDeletable = language != Language.EN

    @IgnoredOnParcel
    val isAuto = language == Language.UNKNOWN

    override fun compareTo(other: TranslationModel): Int {
        return when {
            ordering != other.ordering -> this.ordering - other.ordering
            name != other.name -> name.compareTo(other.name)
            else -> 0
        }
    }
}
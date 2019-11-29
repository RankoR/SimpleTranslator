package com.g2pdev.translation.translation.util

import android.content.res.Resources
import androidx.annotation.StringRes
import com.g2pdev.translation.R
import com.g2pdev.translation.translation.language.Language

interface LanguageNameProvider {
    fun getNameResIdForLanguage(language: Language): Int
    fun getNameForLanguage(language: Language): String
}

class LanguageNameProviderImpl(
    private val resources: Resources
) : LanguageNameProvider {

    @StringRes
    override fun getNameResIdForLanguage(language: Language): Int {
        return when (language) {
            Language.AF -> R.string.language_name_af
            Language.AR -> R.string.language_name_ar
            Language.BE -> R.string.language_name_be
            Language.BG -> R.string.language_name_bg
            Language.BN -> R.string.language_name_bn
            Language.CA -> R.string.language_name_ca
            Language.CS -> R.string.language_name_cs
            Language.CY -> R.string.language_name_cy
            Language.DA -> R.string.language_name_da
            Language.DE -> R.string.language_name_de
            Language.EL -> R.string.language_name_el
            Language.EN -> R.string.language_name_en
            Language.EO -> R.string.language_name_eo
            Language.ES -> R.string.language_name_es
            Language.ET -> R.string.language_name_et
            Language.FA -> R.string.language_name_fa
            Language.FI -> R.string.language_name_fi
            Language.FR -> R.string.language_name_fr
            Language.GA -> R.string.language_name_ga
            Language.GL -> R.string.language_name_gl
            Language.GU -> R.string.language_name_gu
            Language.HE -> R.string.language_name_he
            Language.HI -> R.string.language_name_hi
            Language.HR -> R.string.language_name_hr
            Language.HT -> R.string.language_name_ht
            Language.HU -> R.string.language_name_hu
            Language.ID -> R.string.language_name_id
            Language.IS -> R.string.language_name_is
            Language.IT -> R.string.language_name_it
            Language.JA -> R.string.language_name_ja
            Language.KA -> R.string.language_name_ka
            Language.KN -> R.string.language_name_kn
            Language.KO -> R.string.language_name_ko
            Language.LT -> R.string.language_name_lt
            Language.LV -> R.string.language_name_lv
            Language.MK -> R.string.language_name_mk
            Language.MR -> R.string.language_name_mr
            Language.MS -> R.string.language_name_ms
            Language.MT -> R.string.language_name_mt
            Language.NL -> R.string.language_name_nl
            Language.NO -> R.string.language_name_no
            Language.PL -> R.string.language_name_pl
            Language.PT -> R.string.language_name_pt
            Language.RO -> R.string.language_name_ro
            Language.RU -> R.string.language_name_ru
            Language.SK -> R.string.language_name_sk
            Language.SL -> R.string.language_name_sl
            Language.SQ -> R.string.language_name_sq
            Language.SV -> R.string.language_name_sv
            Language.SW -> R.string.language_name_sw
            Language.TA -> R.string.language_name_ta
            Language.TE -> R.string.language_name_te
            Language.TH -> R.string.language_name_th
            Language.TL -> R.string.language_name_tl
            Language.TR -> R.string.language_name_tr
            Language.UK -> R.string.language_name_uk
            Language.UR -> R.string.language_name_ur
            Language.VI -> R.string.language_name_vi
            Language.ZH -> R.string.language_name_zh
            Language.UNKNOWN -> R.string.language_name_auto
        }
    }

    override fun getNameForLanguage(language: Language): String {
        return resources.getString(getNameResIdForLanguage(language))
    }

}
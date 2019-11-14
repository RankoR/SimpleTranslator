package com.g2pdev.simpletranslator.translation.language

import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage

class LanguageConverter {

    fun convertLanguageToFirebaseCode(language: Language): Int {
        return when (language) {
            Language.AF -> FirebaseTranslateLanguage.AF
            Language.AR -> FirebaseTranslateLanguage.AR
            Language.BE -> FirebaseTranslateLanguage.BE
            Language.BG -> FirebaseTranslateLanguage.BG
            Language.BN -> FirebaseTranslateLanguage.BN
            Language.CA -> FirebaseTranslateLanguage.CA
            Language.CS -> FirebaseTranslateLanguage.CS
            Language.CY -> FirebaseTranslateLanguage.CY
            Language.DA -> FirebaseTranslateLanguage.DA
            Language.DE -> FirebaseTranslateLanguage.DE
            Language.EL -> FirebaseTranslateLanguage.EL
            Language.EN -> FirebaseTranslateLanguage.EN
            Language.EO -> FirebaseTranslateLanguage.EO
            Language.ES -> FirebaseTranslateLanguage.ES
            Language.ET -> FirebaseTranslateLanguage.ET
            Language.FA -> FirebaseTranslateLanguage.FA
            Language.FI -> FirebaseTranslateLanguage.FI
            Language.FR -> FirebaseTranslateLanguage.FR
            Language.GA -> FirebaseTranslateLanguage.GA
            Language.GL -> FirebaseTranslateLanguage.GL
            Language.GU -> FirebaseTranslateLanguage.GU
            Language.HE -> FirebaseTranslateLanguage.HE
            Language.HI -> FirebaseTranslateLanguage.HI
            Language.HR -> FirebaseTranslateLanguage.HR
            Language.HT -> FirebaseTranslateLanguage.HT
            Language.HU -> FirebaseTranslateLanguage.HU
            Language.ID -> FirebaseTranslateLanguage.ID
            Language.IS -> FirebaseTranslateLanguage.IS
            Language.IT -> FirebaseTranslateLanguage.IT
            Language.JA -> FirebaseTranslateLanguage.JA
            Language.KA -> FirebaseTranslateLanguage.KA
            Language.KN -> FirebaseTranslateLanguage.KN
            Language.KO -> FirebaseTranslateLanguage.KO
            Language.LT -> FirebaseTranslateLanguage.LT
            Language.LV -> FirebaseTranslateLanguage.LV
            Language.MK -> FirebaseTranslateLanguage.MK
            Language.MR -> FirebaseTranslateLanguage.MR
            Language.MS -> FirebaseTranslateLanguage.MS
            Language.MT -> FirebaseTranslateLanguage.MT
            Language.NL -> FirebaseTranslateLanguage.NL
            Language.NO -> FirebaseTranslateLanguage.NO
            Language.PL -> FirebaseTranslateLanguage.PL
            Language.PT -> FirebaseTranslateLanguage.PT
            Language.RO -> FirebaseTranslateLanguage.RO
            Language.RU -> FirebaseTranslateLanguage.RU
            Language.SK -> FirebaseTranslateLanguage.SK
            Language.SL -> FirebaseTranslateLanguage.SL
            Language.SQ -> FirebaseTranslateLanguage.SQ
            Language.SV -> FirebaseTranslateLanguage.SV
            Language.SW -> FirebaseTranslateLanguage.SW
            Language.TA -> FirebaseTranslateLanguage.TA
            Language.TE -> FirebaseTranslateLanguage.TE
            Language.TH -> FirebaseTranslateLanguage.TH
            Language.TL -> FirebaseTranslateLanguage.TL
            Language.TR -> FirebaseTranslateLanguage.TR
            Language.UK -> FirebaseTranslateLanguage.UK
            Language.UR -> FirebaseTranslateLanguage.UR
            Language.VI -> FirebaseTranslateLanguage.VI
            Language.ZH -> FirebaseTranslateLanguage.ZH
        }
    }

}
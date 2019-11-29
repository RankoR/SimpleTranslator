package com.g2pdev.translation.translation.language

import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage

class FirebaseLanguageConverter {

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
            Language.UNKNOWN -> 0
        }
    }

    fun convertFirebaseCodeToLanguage(firebaseLanguageCode: Int): Language {
        return when (firebaseLanguageCode) {
            FirebaseTranslateLanguage.AF -> Language.AF
            FirebaseTranslateLanguage.AR -> Language.AR
            FirebaseTranslateLanguage.BE -> Language.BE
            FirebaseTranslateLanguage.BG -> Language.BG
            FirebaseTranslateLanguage.BN -> Language.BN
            FirebaseTranslateLanguage.CA -> Language.CA
            FirebaseTranslateLanguage.CS -> Language.CS
            FirebaseTranslateLanguage.CY -> Language.CY
            FirebaseTranslateLanguage.DA -> Language.DA
            FirebaseTranslateLanguage.DE -> Language.DE
            FirebaseTranslateLanguage.EL -> Language.EL
            FirebaseTranslateLanguage.EN -> Language.EN
            FirebaseTranslateLanguage.EO -> Language.EO
            FirebaseTranslateLanguage.ES -> Language.ES
            FirebaseTranslateLanguage.ET -> Language.ET
            FirebaseTranslateLanguage.FA -> Language.FA
            FirebaseTranslateLanguage.FI -> Language.FI
            FirebaseTranslateLanguage.FR -> Language.FR
            FirebaseTranslateLanguage.GA -> Language.GA
            FirebaseTranslateLanguage.GL -> Language.GL
            FirebaseTranslateLanguage.GU -> Language.GU
            FirebaseTranslateLanguage.HE -> Language.HE
            FirebaseTranslateLanguage.HI -> Language.HI
            FirebaseTranslateLanguage.HR -> Language.HR
            FirebaseTranslateLanguage.HT -> Language.HT
            FirebaseTranslateLanguage.HU -> Language.HU
            FirebaseTranslateLanguage.ID -> Language.ID
            FirebaseTranslateLanguage.IS -> Language.IS
            FirebaseTranslateLanguage.IT -> Language.IT
            FirebaseTranslateLanguage.JA -> Language.JA
            FirebaseTranslateLanguage.KA -> Language.KA
            FirebaseTranslateLanguage.KN -> Language.KN
            FirebaseTranslateLanguage.KO -> Language.KO
            FirebaseTranslateLanguage.LT -> Language.LT
            FirebaseTranslateLanguage.LV -> Language.LV
            FirebaseTranslateLanguage.MK -> Language.MK
            FirebaseTranslateLanguage.MR -> Language.MR
            FirebaseTranslateLanguage.MS -> Language.MS
            FirebaseTranslateLanguage.MT -> Language.MT
            FirebaseTranslateLanguage.NL -> Language.NL
            FirebaseTranslateLanguage.NO -> Language.NO
            FirebaseTranslateLanguage.PL -> Language.PL
            FirebaseTranslateLanguage.PT -> Language.PT
            FirebaseTranslateLanguage.RO -> Language.RO
            FirebaseTranslateLanguage.RU -> Language.RU
            FirebaseTranslateLanguage.SK -> Language.SK
            FirebaseTranslateLanguage.SL -> Language.SL
            FirebaseTranslateLanguage.SQ -> Language.SQ
            FirebaseTranslateLanguage.SV -> Language.SV
            FirebaseTranslateLanguage.SW -> Language.SW
            FirebaseTranslateLanguage.TA -> Language.TA
            FirebaseTranslateLanguage.TE -> Language.TE
            FirebaseTranslateLanguage.TH -> Language.TH
            FirebaseTranslateLanguage.TL -> Language.TL
            FirebaseTranslateLanguage.TR -> Language.TR
            FirebaseTranslateLanguage.UK -> Language.UK
            FirebaseTranslateLanguage.UR -> Language.UR
            FirebaseTranslateLanguage.VI -> Language.VI
            FirebaseTranslateLanguage.ZH -> Language.ZH
            else -> Language.UNKNOWN
        }
    }

}
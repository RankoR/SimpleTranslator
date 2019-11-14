package com.g2pdev.simpletranslator.translation.translator

import com.g2pdev.simpletranslator.translation.language.LanguagePair
import io.reactivex.Single

interface Translator {
    fun translate(languagePair: LanguagePair, text: String): Single<String>
}
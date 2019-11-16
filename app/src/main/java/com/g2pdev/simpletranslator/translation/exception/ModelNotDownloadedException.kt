package com.g2pdev.simpletranslator.translation.exception

import com.g2pdev.simpletranslator.translation.language.LanguagePair

class ModelNotDownloadedException(
    val languagePair: LanguagePair
) : Exception()
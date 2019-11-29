package com.g2pdev.translation.translation.exception

import com.g2pdev.translation.translation.language.LanguagePair

class ModelNotDownloadedException(
    val languagePair: LanguagePair
) : Exception()
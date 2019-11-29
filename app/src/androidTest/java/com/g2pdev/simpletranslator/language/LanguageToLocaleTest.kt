package com.g2pdev.simpletranslator.language

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.translation.translation.language.Language
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LanguageToLocaleTest {

    @Test
    fun testLanguageToLocale() {
        Language
            .values()
            .forEach { language ->
                val locale = language.toLocale()

                Assert.assertEquals(language.getExpectedLanguageCode(), locale.language)
            }
    }

    private fun Language.getExpectedLanguageCode(): String {
        var expectedLanguageCode = name.toLowerCase()

        if (expectedLanguageCode == "he") {
            expectedLanguageCode = "iw" // Old
        }
        if (expectedLanguageCode == "id") {
            expectedLanguageCode = "in" // Old
        }

        return expectedLanguageCode
    }

}
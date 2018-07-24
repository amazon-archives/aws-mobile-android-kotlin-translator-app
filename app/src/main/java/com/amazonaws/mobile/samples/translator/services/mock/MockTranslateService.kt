package com.amazonaws.mobile.samples.translator.services.mock

import android.util.Log
import com.amazonaws.mobile.samples.translator.TAG
import com.amazonaws.mobile.samples.translator.services.TranslateService

/**
 * A mock translation service that just tests the functionality.  This supports two languages,
 * "forward" which will just return the same text, and reverse, which will reverse the text.
 */
class MockTranslateService : TranslateService {
    companion object {
        private val TRANSLATE_LANGUAGES = mapOf(
                "Forward" to 1,
                "Reverse" to 2
        )
    }

    /**
     * Get the list of languages that are supported
     */
    override fun getLanguages(callback: (Map<String, Int>) -> Unit) {
        callback(TRANSLATE_LANGUAGES)
    }

    /**
     * Translate some text according to the language code that is provided, returning the
     * translated text in the callback
     *
     * @param {String} s the string to translate
     * @param {Int} language the language to translate the string into
     */
    override fun translate(s: String, language: Int, callback: (String) -> Unit) {
        Log.d(TAG, "Translate $s to $language")
        return when (language) {
            1 -> callback(s)
            2 -> callback(s.reversed())
            else -> throw IllegalArgumentException()
        }
    }
}
/*
Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy of this
software and associated documentation files (the "Software"), to deal in the Software
without restriction, including without limitation the rights to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
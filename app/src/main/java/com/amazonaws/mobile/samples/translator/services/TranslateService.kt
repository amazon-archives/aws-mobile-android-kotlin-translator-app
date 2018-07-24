package com.amazonaws.mobile.samples.translator.services

interface TranslateService {
    /**
     * Obtain a mapping of the translation languages supported.  This may be pulled from an
     * API or embedded statically.  The mapping is a number -> name of language in the callback.
     */
    fun getLanguages(callback: (Map<String, Int>) -> Unit)

    /**
     * Translate some text according to the language code that is provided, returning the
     * translated text in the callback
     *
     * @param {String} s the string to translate
     * @param {Int} language the language to translate the string into
     */
    fun translate(s: String, language: Int, callback: (String) -> Unit)
}
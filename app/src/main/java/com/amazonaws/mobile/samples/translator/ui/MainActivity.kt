package com.amazonaws.mobile.samples.translator.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.widget.ArrayAdapter
import android.widget.Button
import com.amazonaws.mobile.samples.translator.R
import com.amazonaws.mobile.samples.translator.afterTextChanged
import com.amazonaws.mobile.samples.translator.contentView
import com.amazonaws.mobile.samples.translator.replace
import com.amazonaws.mobile.samples.translator.services.TranslateService
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    companion object {
        /**
         * ID of the callback for doing the speech recognition
         */
        const val REQUEST_SPEECH_RECOGNIZER: Int = 1001
    }

    /**
     * Translation service object
     */
    private val translateService: TranslateService by inject()

    /**
     * The List of languages that are provided by the translate service
     */
    private var languages: Map<String, Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Determine if speech recognition is available.  If it is, enable or disable the record
         * button
         */
        setButtonEnabled(record_button, SpeechRecognizer.isRecognitionAvailable(this))

        /**
         * The Translate and Play buttons get enabled later, so disable them right now
         */
        setButtonEnabled(play_button, false)
        setButtonEnabled(translate_button, false)

        /**
         * The record button uses speech recognition to record some text
         */
        record_button.setOnClickListener {
            if (SpeechRecognizer.isRecognitionAvailable(this)) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                }
                startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER)
            } else {
                Snackbar.make(contentView, "Speech Recognition is not available", Snackbar.LENGTH_LONG).show()
            }
        }

        /**
         * Load the languages into the spinner
         */
        translateService.getLanguages { response ->
            languages = response
            val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, response.keys.toList())
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            language_choice.adapter = adapter
        }

        /**
         * The translate button should only be enabled if there are more than 4 characters
         * in the original_text field.
         */
        original_text.afterTextChanged { text ->
            setButtonEnabled(translate_button, text.length >= 4)
        }

        /**
         * The translate button will trigger the translation service
         */
        translate_button.setOnClickListener {
            val text = original_text.text.toString().trim()
            val language = language_choice.selectedItem
            translateService.translate(text, languages!![language]!!) { response ->
                translated_text.text = response
                setButtonEnabled(play_button, true)
            }
        }

        /**
         * The play button doesn't get pressed until there is a translation available.
         */
        play_button.setOnClickListener {
            Snackbar.make(contentView, "This will play something", Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * Handle callback from the speech recognition service
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SPEECH_RECOGNIZER && resultCode == RESULT_OK) {
            data?.let {
                val results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                original_text.text.replace(results[0])
            }
        }
    }

    /**
     * enable or disable the required button - two versions - one for a button and one for a
     * floating action button
     */
    private fun setButtonEnabled(button: Button, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        button.isClickable = isEnabled
    }

    private fun setButtonEnabled(button: FloatingActionButton, isEnabled: Boolean) {
        button.isEnabled = isEnabled
        button.isClickable = isEnabled
    }
}

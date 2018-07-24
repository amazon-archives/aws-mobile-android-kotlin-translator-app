package com.amazonaws.mobile.samples.translator

import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

/**
 * Replace the entire contents of a CharSequence with something else
 * @param s [String] the replacement string
 */
fun CharSequence.replace(s: String) {
    replaceRange(indices, s)
}

/**
 * Get the content view of the current activity.
 */
inline val AppCompatActivity.contentView
    get() = this.findViewById<View>(android.R.id.content)

/**
 * Define a callback for when a text field changes
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

/**
 * Extension method to get the TAG name for all objects
 * @see <a href="https://kotlinextensions.com">kotlinextensions.com</a>
 */
val <T : Any> T.TAG
    get() = this::class.simpleName

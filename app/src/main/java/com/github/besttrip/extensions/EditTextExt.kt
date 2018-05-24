package com.github.besttrip.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun EditText.afterTextChange(init: (String) -> Unit) {
    if (visibility == View.VISIBLE)
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                init(s?.toString() ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
}

fun EditText.onTextChange(init: (String) -> Unit) {
    if (visibility == View.VISIBLE)
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                init(s?.toString() ?: "")
            }

        })
}

fun Context.clearText(vararg views: EditText?) {
    views.filterNotNull().forEach {
        it.setText("")
    }
}
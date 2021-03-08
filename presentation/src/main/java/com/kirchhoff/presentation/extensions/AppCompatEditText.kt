package com.kirchhoff.presentation.extensions

import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.setTextIfRequired(newText: String) {
    if (newText != text.toString()) {
        setText(newText)
        setSelection(newText.length)
    }
}

fun AppCompatEditText.showError(show: Boolean, @StringRes strRes: Int) {
    error = if (show) {
        resources.getString(strRes)
    } else {
        null
    }
}

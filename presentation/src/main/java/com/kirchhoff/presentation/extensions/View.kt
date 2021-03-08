package com.kirchhoff.presentation.extensions

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

fun View.hideKeyboard() {
    val imm: InputMethodManager = context.getSystemService()!!
    imm.hideSoftInputFromWindow(windowToken, 0)
}

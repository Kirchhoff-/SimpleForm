package com.kirchhoff.presentation.extensions

import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView

@Suppress("SpreadOperator")
fun AppCompatTextView.setFormattedText(@StringRes stringRes: Int, vararg args: Any) {
    text = resources.getString(stringRes, *args)
}

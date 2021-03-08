package com.kirchhoff.presentation.ui.user

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserFragmentArg(
    val name: String,
    val secondName: String,
    val info: String,
    val email: String
) : Parcelable

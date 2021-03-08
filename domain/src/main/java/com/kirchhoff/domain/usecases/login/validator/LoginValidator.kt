package com.kirchhoff.domain.usecases.login.validator

import java.util.regex.Pattern

class LoginValidator : ILoginValidator {

    override fun isValidEmail(email: String): Boolean = Pattern.compile(EMAIL_REGEX).matcher(email).matches()

    override fun isValidPassword(password: String): Boolean = password.length >= MIN_PASSWORD_LENGTH

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private const val EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    }
}

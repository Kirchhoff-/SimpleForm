package com.kirchhoff.domain.usecases.login.validator

interface ILoginValidator {
    fun isValidEmail(email: String): Boolean
    fun isValidPassword(password: String): Boolean
}
package com.kirchhoff.domain.usecases.login.exceptions

import com.kirchhoff.domain.exceptions.IExceptionConverter
import com.kirchhoff.domain.exceptions.exceptions.LoginException
import com.kirchhoff.domain.models.LoginError

class LoginExceptionConverter : IExceptionConverter<LoginError> {

    override fun convert(exception: Exception): LoginError =
        when (exception) {
            is LoginException -> createLoginError(exception)
            else -> LoginError.Other(exception.message ?: exception.toString())
        }

    private fun createLoginError(loginException: LoginException): LoginError =
        when (loginException.errorCode) {
            in 0..100 -> LoginError.ServerError(loginException.errorCode.toString())
            in 101..200 -> LoginError.NetworkError(loginException.errorMessage)
            else -> LoginError.Other("${loginException.errorCode} ${loginException.errorMessage}")
        }
}

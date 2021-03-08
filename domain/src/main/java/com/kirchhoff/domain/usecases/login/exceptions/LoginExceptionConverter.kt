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
            in LOGIN_ERROR_RANGE -> LoginError.ServerError(loginException.errorCode.toString())
            in NETWORK_ERROR_RANGE -> LoginError.NetworkError(loginException.errorMessage)
            else -> LoginError.Other("${loginException.errorCode} ${loginException.errorMessage}")
        }

    companion object {
        private val LOGIN_ERROR_RANGE = 0..100
        private val NETWORK_ERROR_RANGE = 101..200
    }
}

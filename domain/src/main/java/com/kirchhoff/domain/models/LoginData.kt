package com.kirchhoff.domain.models

data class LoginResult(
    val user: User? = null,
    val error: LoginError? = null
)

sealed class LoginError {
    object InvalidEmail : LoginError()
    object InvalidPassword : LoginError()
    data class NetworkError(val message: String) : LoginError()
    data class ServerError(val message: String) : LoginError()
    class Other(val message: String): LoginError()
}
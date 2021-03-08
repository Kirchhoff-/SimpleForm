package com.kirchhoff.domain.usecases.login

import com.kirchhoff.domain.models.LoginResult

interface ILoginUseCase {
    suspend fun login(email: String, password: String): LoginResult
}
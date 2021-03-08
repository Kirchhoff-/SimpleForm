package com.kirchhoff.domain.usecases

import com.kirchhoff.domain.models.LoginResult

interface ILoginUseCase {
    suspend fun login(email: String, password: String): LoginResult
}
package com.kirchhoff.domain.repository

import com.kirchhoff.core.Result
import com.kirchhoff.domain.models.User

interface ILoginRepository {
    suspend fun login(email: String, password: String): Result<User>
}
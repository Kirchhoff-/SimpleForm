package com.kirchhoff.service

import com.kirchhoff.data.models.NetworkUser

interface IUserService {
    suspend fun login(email: String, password: String): NetworkUser
}

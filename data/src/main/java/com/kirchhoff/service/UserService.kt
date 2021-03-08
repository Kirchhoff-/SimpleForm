package com.kirchhoff.service

import com.kirchhoff.data.models.NetworkUser

class UserService: IUserService {

    private val networkUser = NetworkUser("John", "Doe", "Some information", "JohnDoe@gmail.com")

    override suspend fun login(email: String, password: String): NetworkUser =
        if (email != TEST_EMAIL && password != TEST_PASSWORD) {
            networkUser
        } else {
            throw error("Test exception")
        }

    companion object {
        private const val TEST_EMAIL = "test@test.test"
        private const val TEST_PASSWORD = "12345678"
    }
}
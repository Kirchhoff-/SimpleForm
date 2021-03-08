package com.kirchhoff.domain.exceptions.exceptions

class LoginException(val errorMessage: String, val errorCode: Int): Exception(errorCode.toString())
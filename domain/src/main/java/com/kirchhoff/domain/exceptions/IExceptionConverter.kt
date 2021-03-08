package com.kirchhoff.domain.exceptions

interface IExceptionConverter<out T> {
    fun convert(exception: Exception): T
}

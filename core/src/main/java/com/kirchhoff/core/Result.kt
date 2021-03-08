package com.kirchhoff.core

sealed class Result<out T> {
    data class Success<T>(val data: T): Result<T>()
    data class Exception<T>(val exception: kotlin.Exception): Result<T>()
}
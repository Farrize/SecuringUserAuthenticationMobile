package com.farras.securinguserauthenticationmobile.util

sealed class AuthResult<out T: Any> {
    data class Success<out T: Any>(val data: String) : AuthResult<T>()
    data class Error(val message: String?) : AuthResult<Nothing>()
}
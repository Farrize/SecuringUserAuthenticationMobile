package com.farras.securinguserauthenticationmobile.util

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val data: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
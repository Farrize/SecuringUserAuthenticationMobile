package com.farras.securinguserauthenticationmobile.util

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: String) : LoginState()
    data class Error(val message: String) : LoginState()
}
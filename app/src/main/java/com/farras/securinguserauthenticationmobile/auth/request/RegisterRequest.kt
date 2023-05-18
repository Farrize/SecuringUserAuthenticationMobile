package com.farras.securinguserauthenticationmobile.auth.request

data class RegisterRequest(
    val telephone: String,
    val username: String,
    val password: String
)
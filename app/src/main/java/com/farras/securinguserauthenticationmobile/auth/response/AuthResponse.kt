package com.farras.securinguserauthenticationmobile.auth.response

data class AuthResponse(
    val success: Boolean,
    val message: String?,
    val token: String?
)
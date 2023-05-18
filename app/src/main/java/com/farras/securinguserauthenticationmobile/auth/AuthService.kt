package com.farras.securinguserauthenticationmobile.auth

import com.farras.securinguserauthenticationmobile.auth.request.LoginRequest
import com.farras.securinguserauthenticationmobile.auth.request.RegisterRequest
import com.farras.securinguserauthenticationmobile.auth.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    fun register(@Body body: RegisterRequest): AuthResponse

    @POST("auth/login")
    fun login(@Body body: LoginRequest): AuthResponse
}
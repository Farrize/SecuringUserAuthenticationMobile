package com.farras.securinguserauthenticationmobile.auth

import com.farras.securinguserauthenticationmobile.auth.request.LoginRequest
import com.farras.securinguserauthenticationmobile.auth.request.RegisterRequest
import com.farras.securinguserauthenticationmobile.auth.response.AuthResponse
import com.farras.securinguserauthenticationmobile.util.ApiConfig
import com.farras.securinguserauthenticationmobile.util.AuthResult
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val authService: AuthService = Retrofit.Builder()
        .baseUrl(ApiConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AuthService::class.java)

    suspend fun login(
        username: String,
        password: String
    ) : AuthResult<AuthResponse> {
        try {
            val request = LoginRequest(username, password)
            val response = authService.login(request)

            if (response != null) {
                if (response.success) {
                    return AuthResult.Success(response.token!!)
                } else {
                    return AuthResult.Error(response.message)
                }
            } else {
                return AuthResult.Error(response.message)
            }
        } catch (e: Exception) {
            return AuthResult.Error(e.message)
        }
    }

    suspend fun register(
        username: String,
        password: String,
        telephone: String
    ) : AuthResult<AuthResponse> {
        try {
            val request = RegisterRequest(username, password, telephone)
            val response = authService.register(request)

            if (response != null) {
                if (response.success) {
                    return AuthResult.Success(response.token!!)
                } else {
                    return AuthResult.Error(response.message)
                }
            } else {
                return AuthResult.Error(response.message)
            }
        } catch (e: Exception) {
            return AuthResult.Error(e.message)
        }
    }
}
package com.farras.securinguserauthenticationmobile.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import com.farras.securinguserauthenticationmobile.auth.ApiClient
import com.farras.securinguserauthenticationmobile.bcrypt.Bcrypt
import com.farras.securinguserauthenticationmobile.util.AuthResult
import com.farras.securinguserauthenticationmobile.util.AuthState
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            // Enkripsi password dengan Bcrypt
            val encryptedPassword = BCrypt.withDefaults().hashToString(10, password.toCharArray())
            println(encryptedPassword)

            val result = ApiClient().login(username, encryptedPassword)
            println("Data yang dikirim : $username & $encryptedPassword")
            if (result is AuthResult.Success) {
                _authState.value = AuthState.Success(result.data)
            } else if (result is AuthResult.Error) {
                _authState.value = AuthState.Error(result.message ?: "Unknown Error")
            } else {
                _authState.value = AuthState.Error("Not Implemented")
            }
            _authState.value = AuthState.Success("$password $encryptedPassword")
            println()
        }
    }
}
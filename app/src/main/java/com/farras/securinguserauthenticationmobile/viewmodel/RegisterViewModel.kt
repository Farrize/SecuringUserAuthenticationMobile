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

class RegisterViewModel : ViewModel() {
    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    fun register(username: String, password: String, telephone: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            // Enkripsi password dengan Bcrypt pribadi
            val encryptedPassword = BCrypt.withDefaults().hashToString(10, password.toCharArray())
            println(encryptedPassword)

            val result = ApiClient().register(username, encryptedPassword, telephone)
            if (result is AuthResult.Success) {
                _authState.value = AuthState.Success(result.data)
            } else if (result is AuthResult.Error) {
                _authState.value = AuthState.Error(result.message ?: "Unknown Error")
            }
            _authState.value = AuthState.Success("$password $encryptedPassword")
        }
    }
}
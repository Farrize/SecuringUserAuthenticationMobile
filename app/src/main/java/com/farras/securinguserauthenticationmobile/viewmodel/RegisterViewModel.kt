package com.farras.securinguserauthenticationmobile.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import com.farras.securinguserauthenticationmobile.util.AuthState
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    fun register(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            // Enkripsi password dengan library BCrypt
            val encryptedPassword = BCrypt.withDefaults().hashToString(20, password.toCharArray())

            /* Melakukan proses register
            val result = repository.register(username, password)
            if (result is Result.Success) {
                _authState.value = AuthState.Success(result.data)
            } else {
                _authState.value = AuthState.Error("Register failed")
            }
            */
            _authState.value = AuthState.Error("Not Implemented")
        }
    }
}
package com.farras.securinguserauthenticationmobile.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import com.farras.securinguserauthenticationmobile.auth.ApiClient
import com.farras.securinguserauthenticationmobile.util.AuthResult
import com.farras.securinguserauthenticationmobile.util.AuthState
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            // Enkripsi password dengan library BCrypt
            val encryptedPassword = password.let { BCrypt.withDefaults().hashToString(12, it.toCharArray()) }
            println(encryptedPassword)

            println("LoginViewModel.login().viewModelScope.launch().ApiClient().login() called")
            val result = ApiClient().login(username, encryptedPassword)
            println("LoginViewModel.login().viewModelScope.launch().ApiClient().login() finished")
            if (result is AuthResult.Success) {
                _authState.value = AuthState.Success(result.data)
            } else if (result is AuthResult.Error) {
                _authState.value = AuthState.Error(result.message ?: "Unknown Error")
            } else {
                _authState.value = AuthState.Error("Not Implemented")
            }
            println(_authState.value.toString())
            println("LoginViewModel.login().viewModelScope.launch() finished")
        }
    }
}
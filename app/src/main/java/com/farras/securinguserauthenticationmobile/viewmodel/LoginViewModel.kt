package com.farras.securinguserauthenticationmobile.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farras.securinguserauthenticationmobile.util.LoginState
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = mutableStateOf<LoginState>(LoginState.Idle)
    val loginState: State<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            /* Melakukan proses login
            val result = repository.login(username, password)
            if (result is Result.Success) {
                _loginState.value = LoginState.Success(result.data)
            } else {
                _loginState.value = LoginState.Error("Login failed")
            }
            */
            _loginState.value = LoginState.Error("Not Implemented")
        }
    }
}
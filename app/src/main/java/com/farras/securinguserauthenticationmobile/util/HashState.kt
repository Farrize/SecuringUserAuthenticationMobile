package com.farras.securinguserauthenticationmobile.util

sealed class HashState {
    object Idle : HashState()
    object Loading : HashState()
    data class Success(val data: List<String>, val message: Double) : HashState()
    data class Error(val message: String) : HashState()
}
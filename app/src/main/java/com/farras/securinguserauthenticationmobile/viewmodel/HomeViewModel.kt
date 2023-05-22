package com.farras.securinguserauthenticationmobile.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.farras.securinguserauthenticationmobile.bcrypt.Bcrypt
import com.farras.securinguserauthenticationmobile.util.HashState

class HomeViewModel : ViewModel() {
    private val _hashState = mutableStateOf<HashState>(HashState.Idle);
    val hashState: State<HashState> = _hashState

    fun hashPasswords(controlPassword: String, testPasswords: List<String>) {
        _hashState.value = HashState.Loading

        val hashedControlPassword = Bcrypt().encrypt(10, controlPassword)
        val avalancheEffects: ArrayList<Float> = arrayListOf()

        val hashedPasswords = testPasswords.map { password ->
            val hashedPassword = Bcrypt().encrypt(10, password)
            val avalancheEffect = Bcrypt().bitDifference(hashedControlPassword, hashedPassword)
            avalancheEffects.add(avalancheEffect)
            "$password $hashedPassword $avalancheEffect"
        }
        val avalancheEffectAverage = avalancheEffects.average()

        _hashState.value = HashState.Success(hashedPasswords, avalancheEffectAverage)
    }
}
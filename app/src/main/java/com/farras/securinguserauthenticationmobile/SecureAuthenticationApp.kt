package com.farras.securinguserauthenticationmobile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farras.securinguserauthenticationmobile.screen.LoginScreen
import com.farras.securinguserauthenticationmobile.viewmodel.LoginViewModel

@Composable
fun SecureAuthenticationApp() {
    val navController = rememberNavController()
    val loginViewModel by lazy { LoginViewModel() }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    navController.navigate("home")
                }
            )
        }
        composable("home") {
            Text(text = "Home") // Home Screen Not Implemented Yet
        }
    }


}
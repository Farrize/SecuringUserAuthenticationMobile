package com.farras.securinguserauthenticationmobile

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farras.securinguserauthenticationmobile.ui.screen.LoginScreen
import com.farras.securinguserauthenticationmobile.ui.screen.RegisterScreen
import com.farras.securinguserauthenticationmobile.viewmodel.LoginViewModel
import com.farras.securinguserauthenticationmobile.viewmodel.RegisterViewModel

@Composable
fun SecureAuthenticationApp() {
    val navController = rememberNavController()
    val loginViewModel by lazy { LoginViewModel() }
    val registerViewModel by lazy { RegisterViewModel() }
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    )
    { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("login") {
                LoginScreen(
                    viewModel = loginViewModel,
                    scaffoldState = scaffoldState,
                    onLoginSuccess = {
                        navController.navigate("home")
                    }
                )
            }
            composable("register") {
                RegisterScreen(
                    viewModel = registerViewModel,
                    scaffoldState = scaffoldState,
                    onRegisterSuccess = {
                        navController.navigate("home")
                    }
                )
            }
            composable("home") {
                Text(text = "Home") // Home Screen Not Implemented Yet
            }
        }
    }



}
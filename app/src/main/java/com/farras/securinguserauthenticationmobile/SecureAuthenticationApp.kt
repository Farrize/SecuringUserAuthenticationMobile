package com.farras.securinguserauthenticationmobile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.farras.securinguserauthenticationmobile.ui.screen.HomeScreen
import com.farras.securinguserauthenticationmobile.ui.screen.LoginScreen
import com.farras.securinguserauthenticationmobile.ui.screen.RegisterScreen
import com.farras.securinguserauthenticationmobile.viewmodel.HomeViewModel
import com.farras.securinguserauthenticationmobile.viewmodel.LoginViewModel
import com.farras.securinguserauthenticationmobile.viewmodel.RegisterViewModel

@Composable
fun SecureAuthenticationApp() {
    val navController = rememberNavController()
    val loginViewModel by lazy { LoginViewModel() }
    val registerViewModel by lazy { RegisterViewModel() }
    val homeViewModel by lazy { HomeViewModel() }
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
                    onLoginSuccess = { data ->
                        navController.navigate("home")
                    },
                    onClickRegister = {
                        navController.navigate("register")
                    }
                )
            }
            composable("register") {
                RegisterScreen(
                    viewModel = registerViewModel,
                    scaffoldState = scaffoldState,
                    onRegisterSuccess = { data ->
                        navController.navigate("home")
                    },
                    onClickLogin = {
                        navController.navigate("login")
                    }
                )
            }
            composable("home") {
                HomeScreen(
                    viewModel = homeViewModel,
                    scaffoldState = scaffoldState,
                    onHashButtonClicked = {

                    }
                )
            }
            composable("home/{passwords}") { backStackEntry ->
                val passwords = backStackEntry.arguments?.getString("passwords")!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Home")
                    Text(text = passwords)
                }
            }
        }
    }



}
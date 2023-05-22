package com.farras.securinguserauthenticationmobile.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farras.securinguserauthenticationmobile.ui.component.SemiCircularBackground
import com.farras.securinguserauthenticationmobile.util.AuthState
import com.farras.securinguserauthenticationmobile.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    scaffoldState: ScaffoldState,
    onLoginSuccess: (String) -> Unit,
    onClickRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by remember { viewModel.authState }

    when (loginState) {
        is AuthState.Success -> {
            onLoginSuccess((loginState as AuthState.Success).data)
        }
        is AuthState.Error -> {
            LaunchedEffect(key1 = true) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = (loginState as AuthState.Error).message
                )
            }
        }
        is AuthState.Loading -> {
            Text(text = "Loading...")
        }
        else -> {
            // Do Nothing
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                label = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Button(
                onClick = {
                    // viewModel.login(username, password)
                    onLoginSuccess("Login Success")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Login")
            }
            TextButton(onClick = onClickRegister) {
                Text("Register")
            }
        }
    }
}
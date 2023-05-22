package com.farras.securinguserauthenticationmobile.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.farras.securinguserauthenticationmobile.util.AuthState
import com.farras.securinguserauthenticationmobile.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    scaffoldState: ScaffoldState,
    onRegisterSuccess: (String) -> Unit,
    onClickLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var telephone by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val registerState by remember { viewModel.authState }

    when (registerState) {
        is AuthState.Success -> {
            onRegisterSuccess((registerState as AuthState.Success).data)
        }
        is AuthState.Error -> {
            LaunchedEffect(key1 = true) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = (registerState as AuthState.Error).message
                    )
                }
            }
        }
        is AuthState.Loading -> {
            Text(text = "Loading...")
        }
        else -> {
            // Do Nothing
        }
    }

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
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = telephone,
            onValueChange = { telephone = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            label = { Text("Telephone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                if (password != confirmPassword) {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Password and Confirm Password must be same")
                    }
                } else {
                    viewModel.register(username, password, telephone)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Register")
        }
        TextButton(onClick = onClickLogin) {
            Text("Login")
        }
    }
}
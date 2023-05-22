package com.farras.securinguserauthenticationmobile.ui.screen

import android.graphics.Color.rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.farras.securinguserauthenticationmobile.util.HashState
import com.farras.securinguserauthenticationmobile.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    scaffoldState: ScaffoldState,
    onHashButtonClicked: () -> Unit
) {
    var controlPassword by remember { mutableStateOf("") }
    var testPasswords by remember { mutableStateOf("") }
    var avalancheEffectAverage by remember { mutableStateOf(0.0) }
    val coroutineScope = rememberCoroutineScope()
    val hashState by remember { viewModel.hashState }

    when (hashState) {
        is HashState.Success -> {
            onHashButtonClicked()
            val results = hashState as HashState.Success
            testPasswords = results.data
            avalancheEffectAverage = results.message
        }
        is HashState.Error -> {
            LaunchedEffect(key1 = true) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = (hashState as HashState.Error).message
                    )
                }
            }
        }
        is HashState.Loading -> {
            LaunchedEffect(key1 = true) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Loading..."
                    )
                }
            }
        }
        else -> {
            // Do Nothing
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .background(color = Color(rgb(32, 178, 170))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
        ) {

        }
        OutlinedTextField(
            value = avalancheEffectAverage.toString(),
            onValueChange = { avalancheEffectAverage = it.toDouble() },
            readOnly = true,
            label = { Text(text = "Avalanche Effect Average") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = controlPassword,
            onValueChange = { controlPassword = it },
            label = { Text(text = "Control Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Button(
            onClick = { viewModel.hashPasswords(controlPassword, testPasswords.split("\n")) }
        ) {
            Text(text = "Hash")
        }
    }
}
package com.farras.securinguserauthenticationmobile.ui.screen

import android.graphics.Color.rgb
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
    var testPasswords by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var avalancheEffectAverage by remember { mutableStateOf(0.0) }
    val coroutineScope = rememberCoroutineScope()
    val hashState by remember { viewModel.hashState }

    when (hashState) {
        is HashState.Success -> {
            onHashButtonClicked()
            val results = hashState as HashState.Success
            testPasswords = TextFieldValue(results.data)
            avalancheEffectAverage = results.message
            viewModel.toIdle()
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
            .padding(horizontal = 16.dp, vertical = 16.dp),
            //.background(color = Color(rgb(32, 178, 170))),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = testPasswords,
            onValueChange = { testPasswords = it },
            minLines = 1,
            maxLines = 20,
            label = { Text(text = "Test Passwords") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .horizontalScroll(rememberScrollState())
                .verticalScroll(rememberScrollState()),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clickable {
                            testPasswords = TextFieldValue("")
                            avalancheEffectAverage = 0.0
                        }
                )
            }
        )
        OutlinedTextField(
            value = String.format("%.4f", avalancheEffectAverage) + "%",
            onValueChange = { avalancheEffectAverage = it.toDouble() },
            readOnly = true,
            label = { Text(text = "Avalanche Effect Average", textAlign = TextAlign.Center) },
            modifier = Modifier
                .width(195.dp)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
        OutlinedTextField(
            value = controlPassword,
            onValueChange = { controlPassword = it },
            label = { Text(text = "Control Password", textAlign = TextAlign.Center) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        )
        Button(
            onClick = { viewModel.hashPasswords(controlPassword, testPasswords.text.split("\n")) }
        ) {
            Text(text = "Hash")
        }
    }
}
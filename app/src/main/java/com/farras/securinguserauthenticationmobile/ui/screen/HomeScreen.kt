package com.farras.securinguserauthenticationmobile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val coroutineScope = rememberCoroutineScope()
    val hashState by remember { viewModel.hashState }

    when (hashState) {
        is HashState.Success -> {
            onHashButtonClicked()
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
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
    }
}
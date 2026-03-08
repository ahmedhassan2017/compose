package com.einshams.compose.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.einshams.compose.state.UiEffect

@Composable
fun LoginRoute(
    onLoggedIn: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
// Handling One-Time Events (Side Effects)
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is UiEffect.Navigate -> onLoggedIn()
                else -> Unit  // means do nothing
            }
        }
    }

    Column(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login")
        Text(text = "loading=${state.isLoading}")
        state.error?.let { Text(text = "error=${it.message}") }
        Button(onClick = { viewModel.onEmailChanged("emilys"); viewModel.onPasswordChanged("emilyspass"); viewModel.login() }) {
            Text(text = "Login (dummyjson)")
        }
    }
}

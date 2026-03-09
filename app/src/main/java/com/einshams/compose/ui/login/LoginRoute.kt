package com.einshams.compose.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.einshams.compose.state.UiEffect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginRoute(
    onLoggedIn: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is UiEffect.Navigate -> onLoggedIn()
                else -> Unit
            }
        }
    }

    LoginScreen(
        email = state.data.email,
        password = state.data.password,
        isLoading = state.isLoading,
        errorMessage = state.error?.message,
        onEmailChange = viewModel::onEmailChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onSubmitValid = viewModel::login
    )
}

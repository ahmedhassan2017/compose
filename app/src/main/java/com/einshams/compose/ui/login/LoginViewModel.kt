package com.einshams.compose.ui.login

import com.einshams.compose.state.UiEffect
import com.einshams.compose.viewmodel.BaseViewModel
import com.einshams.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginScreenState>(LoginScreenState()) {

    fun onEmailChanged(value: String) {
        updateData { it.copy(email = value) }
    }

    fun onPasswordChanged(value: String) {
        updateData { it.copy(password = value) }
    }

    fun login() {
        val (email, password) = state.value.data
        launchWithState {
            val result = loginUseCase(email = email, password = password)
            result.fold(
                onSuccess = { user ->
                    updateData { it.copy(user = user) }
                    emitEffect(UiEffect.Navigate(route = "home"))
                },
                onFailure = { throwable ->
                    setError(message = throwable.message ?: "Login failed", cause = throwable)
                }
            )
        }
    }
}

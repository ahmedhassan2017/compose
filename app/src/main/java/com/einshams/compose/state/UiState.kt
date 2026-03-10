package com.einshams.compose.state

data class UiState<S>(
    val data: S,
    val isLoading: Boolean = false,
    val error: UiError? = null
)

data class UiError(
    val message: String,
    val cause: Throwable? = null
)

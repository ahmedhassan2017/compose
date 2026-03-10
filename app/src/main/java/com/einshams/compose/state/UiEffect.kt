package com.einshams.compose.state

sealed interface UiEffect {
    data class Navigate(val route: String) : UiEffect
    data class ShowSnackbar(val message: String) : UiEffect
    data class ShowToast(val message: String) : UiEffect
    data object NavigateUp : UiEffect
}

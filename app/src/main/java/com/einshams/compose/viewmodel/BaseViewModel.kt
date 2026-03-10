package com.einshams.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.einshams.compose.state.UiEffect
import com.einshams.compose.state.UiError
import com.einshams.compose.state.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<S>(
    initialData: S
) : ViewModel() {

    private val _state = MutableStateFlow(UiState(data = initialData))
    val state: StateFlow<UiState<S>> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<UiEffect>(extraBufferCapacity = 1)
    val effect: SharedFlow<UiEffect> = _effect.asSharedFlow()

    protected fun updateData(reducer: (S) -> S) {
        _state.update { current -> current.copy(data = reducer(current.data)) }
    }

    protected fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    protected fun setError(message: String, cause: Throwable? = null) {
        _state.update { it.copy(isLoading = false, error = UiError(message = message, cause = cause)) }
    }

    protected fun clearError() {
        _state.update { it.copy(error = null) }
    }

    protected fun emitEffect(effect: UiEffect) {
        _effect.tryEmit(effect)
    }

    protected fun launchWithState(
        showLoading: Boolean = true,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            if (showLoading) setLoading(true)
            try {
                block()
                if (showLoading) setLoading(false)
            } catch (t: Throwable) {
                setError(message = t.message ?: "Unknown error", cause = t)
            }
        }
    }
}

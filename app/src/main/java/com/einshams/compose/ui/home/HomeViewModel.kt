package com.einshams.compose.ui.home

import com.einshams.compose.viewmodel.BaseViewModel
import com.einshams.domain.usecase.GetHomeItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeItemsUseCase: GetHomeItemsUseCase
) : BaseViewModel<HomeScreenState>(HomeScreenState()) {

    init {
        loadItems()
    }

    fun loadItems() {
        clearError()
        launchWithState {
            val result = getHomeItemsUseCase()
            result.fold(
                onSuccess = { items ->
                    clearError()
                    updateData { it.copy(items = items) }
                },
                onFailure = { throwable ->
                    setError(
                        message = throwable.message ?: "Failed to load items",
                        cause = throwable
                    )
                }
            )
        }
    }
}

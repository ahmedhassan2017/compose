package com.einshams.compose.ui.details

import androidx.lifecycle.SavedStateHandle
import com.einshams.compose.navigation.Routes
import com.einshams.compose.viewmodel.BaseViewModel
import com.einshams.domain.usecase.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<DetailsScreenState>(DetailsScreenState()) {

    private val productId: String = checkNotNull(savedStateHandle[Routes.DetailsIdArg])

    init {
        loadDetail()
    }

    fun loadDetail() {
        clearError()
        launchWithState {
            val result = getProductDetailUseCase(productId)
            result.fold(
                onSuccess = { product ->
                    updateData { it.copy(product = product) }
                },
                onFailure = { throwable ->
                    setError(
                        message = throwable.message ?: "Failed to load details",
                        cause = throwable
                    )
                }
            )
        }
    }
}

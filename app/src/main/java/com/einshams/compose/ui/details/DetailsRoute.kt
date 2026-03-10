package com.einshams.compose.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailsRoute(
    id: String,
    onBack: () -> Unit,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    DetailsScreen(
        isLoading    = state.isLoading,
        product      = state.data.product,
        errorMessage = state.error?.message,
        onBack       = onBack,
        onRetry      = viewModel::loadDetail
    )
}

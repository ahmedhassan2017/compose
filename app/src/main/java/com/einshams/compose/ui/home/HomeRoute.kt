package com.einshams.compose.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    onOpenDetails: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        isLoading    = state.isLoading,
        items        = state.data.items,
        errorMessage = state.error?.message,
        onItemClick  = onOpenDetails,
        onRetry      = viewModel::loadItems
    )
}

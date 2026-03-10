package com.einshams.compose.ui.home

import com.einshams.domain.model.HomeItem

data class HomeScreenState(
    val items: List<HomeItem> = emptyList()
)

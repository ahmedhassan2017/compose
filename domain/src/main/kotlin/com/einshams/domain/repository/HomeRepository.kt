package com.einshams.domain.repository

import com.einshams.domain.model.HomeItem

interface HomeRepository {
    suspend fun getItems(): Result<List<HomeItem>>
}

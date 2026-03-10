package com.einshams.data.repository

import com.einshams.data.mapper.toDomain
import com.einshams.data.remote.api.HomeApi
import com.einshams.domain.model.HomeItem
import com.einshams.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val homeApi: HomeApi
) : HomeRepository {

    override suspend fun getItems(): Result<List<HomeItem>> {
        return runCatching {
            val response = homeApi.getItems()
            if (!response.isSuccessful) {
                throw IllegalStateException("Items request failed with code ${response.code()}")
            }

            val body = response.body() ?: throw IllegalStateException("Items response body is empty")
            body.products.map { it.toDomain() }
        }
    }
}

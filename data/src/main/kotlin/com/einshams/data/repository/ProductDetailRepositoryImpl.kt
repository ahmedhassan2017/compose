package com.einshams.data.repository

import com.einshams.data.mapper.toDomain
import com.einshams.data.remote.api.ProductDetailApi
import com.einshams.domain.model.ProductDetail
import com.einshams.domain.repository.ProductDetailRepository

class ProductDetailRepositoryImpl(
    private val productDetailApi: ProductDetailApi
) : ProductDetailRepository {

    override suspend fun getProductDetail(id: String): Result<ProductDetail> {
        return runCatching {
            val response = productDetailApi.getProductDetail(id)
            if (!response.isSuccessful) {
                throw IllegalStateException("Product detail request failed with code ${response.code()}")
            }
            val body = response.body() ?: throw IllegalStateException("Product detail response body is empty")
            body.toDomain()
        }
    }
}

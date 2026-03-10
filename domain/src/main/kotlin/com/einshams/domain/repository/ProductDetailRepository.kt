package com.einshams.domain.repository

import com.einshams.domain.model.ProductDetail

interface ProductDetailRepository {
    suspend fun getProductDetail(id: String): Result<ProductDetail>
}

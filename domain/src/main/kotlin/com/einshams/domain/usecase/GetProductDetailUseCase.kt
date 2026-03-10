package com.einshams.domain.usecase

import com.einshams.domain.model.ProductDetail
import com.einshams.domain.repository.ProductDetailRepository
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val productDetailRepository: ProductDetailRepository
) {
    suspend operator fun invoke(id: String): Result<ProductDetail> {
        return productDetailRepository.getProductDetail(id)
    }
}

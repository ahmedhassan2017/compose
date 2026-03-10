package com.einshams.data.remote.api

import com.einshams.data.remote.dto.ProductDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailApi {
    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: String): Response<ProductDetailDto>
}

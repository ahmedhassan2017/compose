package com.einshams.data.remote.api

import com.einshams.data.remote.dto.HomeItemsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("products")
    suspend fun getItems(
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): Response<HomeItemsResponseDto>
}

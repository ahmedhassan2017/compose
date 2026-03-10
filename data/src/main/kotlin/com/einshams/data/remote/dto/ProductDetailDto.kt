package com.einshams.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDetailDto(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: String,
    val images: List<String>,
    val brand: String?,
    val category: String,
    val rating: Double,
    val reviews: List<ReviewDto>
)

data class ReviewDto(
    @SerializedName("reviewerName")
    val reviewerName: String,
    @SerializedName("reviewerEmail")
    val reviewerEmail: String,
    val comment: String,
    val rating: Int
)

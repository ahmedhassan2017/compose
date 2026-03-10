package com.einshams.domain.model

data class ProductDetail(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val brand: String,
    val category: String,
    val rating: Double,
    val reviews: List<Review>
)

data class Review(
    val reviewerName: String,
    val reviewerAvatarUrl: String,
    val comment: String,
    val rating: Int
)

package com.einshams.data.mapper

import com.einshams.data.remote.dto.ProductDetailDto
import com.einshams.data.remote.dto.ReviewDto
import com.einshams.domain.model.ProductDetail
import com.einshams.domain.model.Review

fun ProductDetailDto.toDomain(): ProductDetail {
    return ProductDetail(
        id = id.toString(),
        title = title,
        description = description,
        imageUrl = images.firstOrNull() ?: thumbnail,
        thumbnailUrl = thumbnail,
        brand = brand ?: category,
        category = category,
        rating = rating,
        reviews = reviews.mapIndexed { index, review -> review.toDomain(index) }
    )
}

private fun ReviewDto.toDomain(index: Int): Review {
    return Review(
        reviewerName = reviewerName,
        reviewerAvatarUrl = "https://picsum.photos/seed/${reviewerEmail.hashCode()}/200",
        comment = comment,
        rating = rating
    )
}

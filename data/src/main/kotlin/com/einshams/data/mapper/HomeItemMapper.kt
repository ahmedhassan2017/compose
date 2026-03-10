package com.einshams.data.mapper

import com.einshams.data.remote.dto.HomeItemDto
import com.einshams.domain.model.HomeItem

fun HomeItemDto.toDomain(): HomeItem {
    val subtitleSource = brand?.takeIf { it.isNotBlank() } ?: category

    return HomeItem(
        id = id.toString(),
        title = title,
        subtitle = "#$subtitleSource",
        imageUrl = imageUrl
    )
}

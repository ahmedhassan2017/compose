package com.einshams.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HomeItemDto(
    val id: Int,
    val title: String,
    val category: String,
    val brand: String?,
    @SerializedName("thumbnail")
    val imageUrl: String
)

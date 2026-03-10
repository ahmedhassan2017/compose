package com.einshams.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DummyLoginResponseDto(
    val id: Int,
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    val token: String
)

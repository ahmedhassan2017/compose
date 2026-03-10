package com.einshams.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RemoteUserDto(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val email: String
)

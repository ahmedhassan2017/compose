package com.einshams.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id")
    val remoteId: String,
    @SerializedName("full_name")
    val displayName: String,
    @SerializedName("email_address")
    val emailAddress: String
)

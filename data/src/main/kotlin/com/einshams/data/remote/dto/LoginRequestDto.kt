package com.einshams.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    // DummyJSON uses "username" not "email"; we keep domain signature as email/password.
    @SerializedName("username")
    val email: String,
    val password: String
)

package com.einshams.data.remote.api

import com.einshams.data.remote.dto.LoginRequestDto
import com.einshams.data.remote.dto.DummyLoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): Response<DummyLoginResponseDto>
}

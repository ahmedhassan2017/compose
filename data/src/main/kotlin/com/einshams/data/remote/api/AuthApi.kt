package com.einshams.data.remote.api

import com.einshams.data.remote.dto.LoginRequestDto
import com.einshams.data.remote.dto.LoginResponseDto
import com.einshams.data.remote.dto.RemoteUserResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): Response<LoginResponseDto>

    @GET("users/2")
    suspend fun getLoggedInUser(): Response<RemoteUserResponseDto>
}

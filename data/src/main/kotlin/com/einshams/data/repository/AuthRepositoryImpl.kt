package com.einshams.data.repository

import com.einshams.data.mapper.toDomain
import com.einshams.data.mapper.toUserDto
import com.einshams.data.remote.api.AuthApi
import com.einshams.data.remote.dto.LoginRequestDto
import com.einshams.domain.model.User
import com.einshams.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return runCatching {
            val loginResponse = authApi.login(LoginRequestDto(email = email, password = password))
            if (!loginResponse.isSuccessful) {
                throw IllegalStateException("Login failed with code ${loginResponse.code()}")
            }
            val token = loginResponse.body()?.token
            if (token.isNullOrBlank()) {
                throw IllegalStateException("Login token is empty")
            }

            val userResponse = authApi.getLoggedInUser()
            if (!userResponse.isSuccessful) {
                throw IllegalStateException("Fetch user failed with code ${userResponse.code()}")
            }
            val userDto = userResponse.body()?.data?.toUserDto()
                ?: throw IllegalStateException("User payload is empty")

            userDto.toDomain()
        }
    }
}

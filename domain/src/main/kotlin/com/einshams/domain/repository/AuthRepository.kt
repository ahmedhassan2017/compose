package com.einshams.domain.repository

import com.einshams.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
}

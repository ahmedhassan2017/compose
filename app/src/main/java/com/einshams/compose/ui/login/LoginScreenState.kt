package com.einshams.compose.ui.login

import com.einshams.domain.model.User

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val user: User? = null
)

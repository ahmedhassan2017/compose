package com.einshams.compose.ui.login

import android.util.Patterns

fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()

fun isValidPassword(password: String): Boolean = password.length >= 6


package com.einshams.compose.ui.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import kotlinx.coroutines.launch

private const val TAG = "LoginScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    email: String,
    password: String,
    isLoading: Boolean,
    errorMessage: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmitValid: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var passwordVisible by remember { mutableStateOf(false) }
    var showErrors by remember { mutableStateOf(false) }

    val emailValid = true
    val passwordValid = isValidPassword(password)
    val inputsValid = emailValid && passwordValid

    fun submitLogin() {
        if (!inputsValid) {
            scope.launch { snackbarHostState.showSnackbar("Invalid credentials") }
            return
        }
        scope.launch { snackbarHostState.showSnackbar("Logging in...") }
        Log.i(TAG, "LoginScreen: submitLogin called")
        onSubmitValid()
    }

    LaunchedEffect(errorMessage) {
        if (!errorMessage.isNullOrBlank()) {
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF111318), Color(0xFF0B0D12))))
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        onEmailChange(it)
                        if (showErrors) showErrors = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email Address") },
                    leadingIcon = {
                        Icon(
                            imageVector = if (email.contains("@")) Icons.Default.Email else Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    isError = showErrors && !emailValid,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF151922),
                        unfocusedContainerColor = Color(0xFF151922),
                        focusedBorderColor = Color(0xFF5B77FF),
                        unfocusedBorderColor = Color(0xFF2A2F3A),
                        errorBorderColor = Color(0xFFFF5C5C),
                        focusedLabelColor = Color(0xFF9AA7C7),
                        unfocusedLabelColor = Color(0xFF7D8599),
                        errorLabelColor = Color(0xFFFF5C5C),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(18.dp),
                    supportingText = {
                        if (showErrors && !emailValid) {
                            Text(text = "Invalid format", color = Color(0xFFFF5C5C))
                        }
                    }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        onPasswordChange(it)
                        if (showErrors) showErrors = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible }
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    isError = showErrors && !passwordValid,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                        onDone = {
                            showErrors = true
                            submitLogin()
                        }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF151922),
                        unfocusedContainerColor = Color(0xFF151922),
                        focusedBorderColor = Color(0xFF5B77FF),
                        unfocusedBorderColor = Color(0xFF2A2F3A),
                        errorBorderColor = Color(0xFFFF5C5C),
                        focusedLabelColor = Color(0xFF9AA7C7),
                        unfocusedLabelColor = Color(0xFF7D8599),
                        errorLabelColor = Color(0xFFFF5C5C),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(18.dp),
                    supportingText = {
                        if (showErrors && !passwordValid) {
                            Text(text = "Password must be at least 6 characters", color = Color(0xFFFF5C5C))
                        }
                    }
                )

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = {
                        showErrors = true
                        submitLogin()
                    },
                    enabled = inputsValid && !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF5B77FF),
                        disabledContainerColor = Color(0xFF2A2F3A),
                        contentColor = Color.White,
                        disabledContentColor = Color(0xFF9AA7C7)
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(10.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(text = "Logging in...")
                    } else {
                        Text(text = "Login")
                    }
                }
            }
        }
    }
}


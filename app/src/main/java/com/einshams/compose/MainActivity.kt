package com.einshams.compose

// snackbar with a message and a dismiss button
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction.Companion.Done
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.compareTo

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginContainer()
        }
    }
}
@Composable
fun LoginContainer() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 2. Logic: Validation
    val isEmailValid = email.contains("@") && email.endsWith(".com")
    val isPasswordValid = password.length >= 6
    val isFormFilled = email.isNotBlank() && password.isNotBlank()

    LoginScreen(
        email = email,
        password = password,
        // 2. Button is enabled only if fields are not empty
        isLoginEnabled = isFormFilled,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLoginClick = {
            scope.launch {
                // 3. Validate credentials on click
                if (isEmailValid && isPasswordValid) {
                    snackbarHostState.showSnackbar(
                        message = "Logging in as $email...",
                        actionLabel = "Dismiss",
                        withDismissAction = true
                    )
                } else {
                    snackbarHostState.showSnackbar(
                        message = "Invalid credentials",
                        actionLabel = "Retry",
                        withDismissAction = true
                    )
                }
            }
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun LoginScreen(
        email: String,
        password: String,
        isLoginEnabled: Boolean,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onLoginClick: () -> Unit,
        snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                // 1. Masking the password
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = Password,
                    imeAction = Done
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onLoginClick,
                // 2. Disable button if fields are empty
                enabled = isLoginEnabled
            ) {
                Text("Login")
            }
        }
    }
}
// Update your setContent in MainActivity
// setContent { LoginContainer() }


@Preview(showBackground = true, name = "Filled Login")
@Composable
fun PreviewLoginFilled() {
    LoginScreen(
        email = "test@me.com",
        password = "password123",
        isLoginEnabled = true,
        onEmailChange = {}, // No-op: The preview doesn't need to actually update
        onPasswordChange = {},
        onLoginClick = {},
        snackbarHostState = remember { SnackbarHostState() }
    )
}




 @Composable fun CounterScreen(count: Int, onIncrement: () -> Unit)
{
    Column {
        Text(text = "Count: $count", modifier = Modifier.padding(16.dp))
        Button(onClick = onIncrement) {
            Text(text = "Increment")
        }
    }


}
@Composable fun CounterContainer()
{
    var count by remember { mutableIntStateOf(0) }
    CounterScreen(count, onIncrement = { count++ })

}




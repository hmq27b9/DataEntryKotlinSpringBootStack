package com.dataentrykotlinspringbootstack.frontend.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dataentrykotlinspringbootstack.frontend.controllers.AuthController

@Composable
fun LoginScreen(
    controller: AuthController,
    onLoginSuccess: () -> Unit
) {
    val state by controller.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.loggedIn) {
        if (state.loggedIn) onLoginSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Sign in",
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            BwTextField(
                value = state.identifier,
                onValueChange = controller::onIdentifierChange,
                placeholder = "Email or username",
                enabled = !state.loading
            )

            BwTextField(
                value = state.password,
                onValueChange = controller::onPasswordChange,
                placeholder = "Password",
                enabled = !state.loading,
                password = true
            )

            if (state.error != null) {
                Text(state.error!!, color = Color.Black, fontSize = 14.sp)
            }

            BwButton(
                label = if (state.loading) "Signing in..." else "Login",
                enabled = !state.loading,
                onClick = controller::login
            )
        }
    }
}

@Composable
fun BwTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    password: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .background(Color.White)
            .padding(12.dp)
    ) {
        if (value.isEmpty()) {
            Text(placeholder, color = Color.Gray, fontSize = 16.sp)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            visualTransformation = if (password) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BwButton(
    label: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .background(if (enabled) Color.Black else Color.White)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            color = if (enabled) Color.White else Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
package com.dataentrykotlinspringbootstack.frontend.controllers

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dataentrykotlinspringbootstack.frontend.models.LoginRequest
import com.dataentrykotlinspringbootstack.frontend.models.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

class TokenStore(context: Context) {
    private val prefs = context.applicationContext
        .getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun saveToken(token: String) = prefs.edit { putString("jwt", token) }
    fun getToken(): String? = prefs.getString("jwt", null)
    fun clear() = prefs.edit { remove("jwt") }
}

class AuthInterceptor(private val tokenStore: TokenStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenStore.getToken()
        val request = if (token != null) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else chain.request()
        return chain.proceed(request)
    }
}

data class LoginUiState(
    val identifier: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val loggedIn: Boolean = false
)

class AuthController(
    private val userRepository: UserRepository,
    private val tokenStore: TokenStore
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    fun onIdentifierChange(value: String) {
        _state.value = _state.value.copy(identifier = value, error = null)
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value, error = null)
    }

    fun login() {
        val current = _state.value
        if (current.identifier.isBlank() || current.password.isBlank()) {
            _state.value = current.copy(error = "Both fields are required")
            return
        }
        viewModelScope.launch {
            _state.value = current.copy(loading = true, error = null)
            try {
                val response = userRepository.login(
                    LoginRequest(current.identifier.trim(), current.password)
                )
                tokenStore.saveToken(response.token)
                _state.value = _state.value.copy(loading = false, loggedIn = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Login failed"
                )
            }
        }
    }

    fun logout() {
        tokenStore.clear()
        _state.value = LoginUiState()
    }
}
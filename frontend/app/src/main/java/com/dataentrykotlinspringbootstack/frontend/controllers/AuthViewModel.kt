package com.dataentrykotlinspringbootstack.frontend.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dataentrykotlinspringbootstack.frontend.models.LoginRequest
import com.dataentrykotlinspringbootstack.frontend.network.ApiService
import com.dataentrykotlinspringbootstack.frontend.storage.TokenStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val identifier: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val loggedIn: Boolean = false
)

class AuthViewModel(
    private val apiService: ApiService,
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
                val response = apiService.login(
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
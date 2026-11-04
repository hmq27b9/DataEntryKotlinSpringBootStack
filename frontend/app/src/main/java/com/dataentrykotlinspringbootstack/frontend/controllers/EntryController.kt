package com.dataentrykotlinspringbootstack.frontend.controllers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dataentrykotlinspringbootstack.frontend.models.EntryRepository
import com.dataentrykotlinspringbootstack.frontend.models.EntryRequest
import com.dataentrykotlinspringbootstack.frontend.models.EntryResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EntryUiState(
    val entries: List<EntryResponse> = emptyList(),
    val input: String = "",
    val loading: Boolean = false,
    val submitting: Boolean = false,
    val error: String? = null
)

class EntryController(private val repository: EntryRepository) : ViewModel() {

    private val _state = MutableStateFlow(EntryUiState())
    val state: StateFlow<EntryUiState> = _state.asStateFlow()

    init { refresh() }

    fun onInputChange(value: String) {
        _state.value = _state.value.copy(input = value, error = null)
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val entries = repository.listEntries()
                _state.value = _state.value.copy(entries = entries, loading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Failed to load entries"
                )
            }
        }
    }

    fun submit() {
        val content = _state.value.input.trim()
        if (content.isEmpty()) return
        viewModelScope.launch {
            _state.value = _state.value.copy(submitting = true, error = null)
            try {
                repository.createEntry(EntryRequest(content))
                _state.value = _state.value.copy(input = "", submitting = false)
                refresh()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    submitting = false,
                    error = e.message ?: "Failed to save entry"
                )
            }
        }
    }
}
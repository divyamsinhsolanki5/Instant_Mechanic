package com.example.instantmechanic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instantmechanic.data.MechanicRepository
import com.example.instantmechanic.model.Mechanic
import com.example.instantmechanic.model.ServiceRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface UiState {
    object Loading : UiState
    data class Success(val mechanics: List<Mechanic>) : UiState
    data class Error(val message: String) : UiState
}

class MechanicViewModel(
    private val repository: MechanicRepository = MechanicRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _requestSubmitted = MutableStateFlow(false)
    val requestSubmitted = _requestSubmitted.asStateFlow()

    private val _bookings = MutableStateFlow<List<ServiceRequest>>(emptyList())
    val bookings = _bookings.asStateFlow()

    init {
        fetchMechanics()
    }

    fun fetchMechanics() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getMechanics()
                .onSuccess { list -> _uiState.value = UiState.Success(list) }
                .onFailure { error -> _uiState.value = UiState.Error(error.message ?: "Failed to load data") }
        }
    }

    fun submitRequest(request: ServiceRequest) {
        viewModelScope.launch {
            repository.submitServiceRequest(request)
                .onSuccess {
                    _bookings.value = repository.getBookedRequests()
                    _requestSubmitted.value = true
                }
        }
    }

    fun cancelBooking(request: ServiceRequest) {
        repository.cancelServiceRequest(request)
        _bookings.value = repository.getBookedRequests()
    }

    fun resetSubmissionState() {
        _requestSubmitted.value = false
    }
}
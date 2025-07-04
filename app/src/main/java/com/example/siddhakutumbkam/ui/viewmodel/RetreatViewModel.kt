package com.example.siddhakutumbkam.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siddhakutumbkam.data.model.Retreat
import com.example.siddhakutumbkam.data.model.RetreatBooking
import com.example.siddhakutumbkam.data.repository.RetreatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RetreatUiState(
    val retreats: List<Retreat> = emptyList(),
    val selectedRetreat: Retreat? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class BookingUiState(
    val isBooking: Boolean = false,
    val bookingSuccess: Boolean = false,
    val bookingError: String? = null,
    val completedBooking: RetreatBooking? = null
)

class RetreatViewModel(
    private val repository: RetreatRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RetreatUiState())
    val uiState: StateFlow<RetreatUiState> = _uiState.asStateFlow()
    
    private val _bookingState = MutableStateFlow(BookingUiState())
    val bookingState: StateFlow<BookingUiState> = _bookingState.asStateFlow()
    
    init {
        loadRetreats()
    }
    
    fun loadRetreats() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            repository.getUpcomingRetreats()
                .onSuccess { retreats ->
                    _uiState.value = _uiState.value.copy(
                        retreats = retreats,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load retreats"
                    )
                }
        }
    }
    
    fun selectRetreat(retreatId: String) {
        viewModelScope.launch {
            repository.getRetreatById(retreatId)
                .onSuccess { retreat ->
                    _uiState.value = _uiState.value.copy(selectedRetreat = retreat)
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = exception.message ?: "Failed to load retreat details"
                    )
                }
        }
    }
    
    fun bookRetreat(
        retreatId: String,
        participantName: String,
        participantEmail: String,
        participantPhone: String,
        emergencyContact: String,
        emergencyPhone: String,
        specialRequirements: String
    ) {
        viewModelScope.launch {
            _bookingState.value = _bookingState.value.copy(
                isBooking = true,
                bookingError = null,
                bookingSuccess = false
            )
            
            repository.bookRetreat(
                retreatId = retreatId,
                participantName = participantName,
                participantEmail = participantEmail,
                participantPhone = participantPhone,
                emergencyContact = emergencyContact,
                emergencyPhone = emergencyPhone,
                specialRequirements = specialRequirements
            )
                .onSuccess { booking ->
                    _bookingState.value = _bookingState.value.copy(
                        isBooking = false,
                        bookingSuccess = true,
                        completedBooking = booking
                    )
                }
                .onFailure { exception ->
                    _bookingState.value = _bookingState.value.copy(
                        isBooking = false,
                        bookingError = exception.message ?: "Failed to book retreat"
                    )
                }
        }
    }
    
    fun clearBookingState() {
        _bookingState.value = BookingUiState()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
    
    fun refreshRetreats() {
        loadRetreats()
    }
}

package com.example.siddhakutumbkam.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.siddhakutumbkam.data.model.YouTubeVideo
import com.example.siddhakutumbkam.data.repository.YouTubeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class YouTubeUiState(
    val videos: List<YouTubeVideo> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class YouTubeViewModel(
    private val repository: YouTubeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(YouTubeUiState())
    val uiState: StateFlow<YouTubeUiState> = _uiState.asStateFlow()
    
    init {
        loadVideos()
    }
    
    fun loadVideos() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            repository.getLatestVideos()
                .onSuccess { videos ->
                    _uiState.value = _uiState.value.copy(
                        videos = videos,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }
    
    fun refreshVideos() {
        loadVideos()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

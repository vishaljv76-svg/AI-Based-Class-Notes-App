package com.example.Simtas.ui.transcript

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [TranscriptScreen] adhering to MVVM architecture.
 */
class TranscriptViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptUiState())
    val uiState: StateFlow<TranscriptUiState> = _uiState.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun togglePlayPause() {
        _uiState.update { it.copy(isPlaying = !it.isPlaying) }
    }

    fun onEditTextClicked() {
        // Edit transcript text
    }

    fun onGenerateNotesClicked() {
        _uiState.update { it.copy(isLoading = true) }
        // Process AI notes generation from transcript
    }
}

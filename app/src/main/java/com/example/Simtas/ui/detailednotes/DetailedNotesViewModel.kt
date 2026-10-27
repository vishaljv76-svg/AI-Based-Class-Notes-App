package com.example.Simtas.ui.detailednotes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [DetailedNotesScreen] adhering to MVVM architecture.
 */
class DetailedNotesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DetailedNotesUiState())
    val uiState: StateFlow<DetailedNotesUiState> = _uiState.asStateFlow()

    fun toggleBookmark() {
        _uiState.update { it.copy(isBookmarked = !it.isBookmarked) }
    }

    fun onDownloadPdfClicked() {
        _uiState.update { it.copy(isLoading = true) }
        // Process PDF export / download
    }
}

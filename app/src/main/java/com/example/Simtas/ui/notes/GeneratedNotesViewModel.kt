package com.example.Simtas.ui.notes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [GeneratedNotesScreen] adhering to MVVM architecture.
 */
class GeneratedNotesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GeneratedNotesUiState())
    val uiState: StateFlow<GeneratedNotesUiState> = _uiState.asStateFlow()

    fun onViewFullNotesClicked() {
        // Open full notes view
    }

    fun onTakeQuizClicked() {
        // Open quiz screen
    }

    fun onSaveNotesClicked() {
        _uiState.update { it.copy(isSaved = true) }
    }
}

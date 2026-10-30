package com.example.Simtas.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Simtas.data.repository.NotesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for [GeneratedNotesScreen] adhering to MVVM architecture with real API saving.
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
        onSaveNotesClicked({})
    }

    fun onSaveNotesClicked(onSuccess: (String?) -> Unit = {}) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val noteId = NotesRepositoryImpl.createNote(
                title = _uiState.value.lectureTitle,
                subject = _uiState.value.subjectBadge,
                summary = _uiState.value.aiSummaryText,
                keyTakeaways = _uiState.value.keyTakeaways,
                importantTerms = _uiState.value.importantTerms
            )
            _uiState.update { it.copy(isLoading = false, isSaved = true) }
            onSuccess(noteId)
        }
    }
}

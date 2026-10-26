package com.example.Simtas.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [DashboardScreen] adhering to MVVM architecture.
 */
class DashboardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun selectTab(tab: HomeBottomTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onRecordLectureClicked() {
        // Record lecture action
    }

    fun onUploadAudioClicked() {
        // Upload audio action
    }

    fun onMyNotesClicked() {
        // Open my notes action
    }

    fun onAiQuizClicked() {
        // Open AI quiz action
    }

    fun onNoteItemClicked(noteId: String) {
        // Open specific note
    }
}

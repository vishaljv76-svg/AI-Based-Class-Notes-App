package com.example.Simtas.ui.upload

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [UploadLectureScreen] adhering to MVVM architecture.
 */
class UploadLectureViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UploadLectureUiState())
    val uiState: StateFlow<UploadLectureUiState> = _uiState.asStateFlow()

    fun onBrowseFilesClicked() {
        _uiState.update {
            it.copy(
                isFileSelected = true,
                isUploading = true,
                uploadProgressPercent = 78
            )
        }
    }

    fun onCancelUploadClicked() {
        _uiState.update {
            it.copy(
                isFileSelected = false,
                isUploading = false,
                uploadProgressPercent = 0
            )
        }
    }

    fun onContinueClicked() {
        // Process uploaded file
    }
}

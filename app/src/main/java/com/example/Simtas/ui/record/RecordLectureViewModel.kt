package com.example.Simtas.ui.record

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [RecordLectureScreen] adhering to MVVM architecture.
 */
class RecordLectureViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RecordLectureUiState())
    val uiState: StateFlow<RecordLectureUiState> = _uiState.asStateFlow()

    fun onPauseClicked() {
        _uiState.update {
            it.copy(
                status = RecordingStatus.PAUSED,
                statusText = "Recording paused"
            )
        }
    }

    fun onResumeClicked() {
        _uiState.update {
            it.copy(
                status = RecordingStatus.RECORDING,
                statusText = "Recording in progress"
            )
        }
    }

    fun onStopClicked() {
        _uiState.update {
            it.copy(
                status = RecordingStatus.STOPPED,
                statusText = "Recording saved"
            )
        }
    }
}

package com.example.Simtas.ui.record

enum class RecordingStatus {
    RECORDING,
    PAUSED,
    STOPPED
}

/**
 * UI State for Record Lecture Screen following MVVM architecture.
 */
data class RecordLectureUiState(
    val courseCode: String = "Computer Science - CS301",
    val lectureTitle: String = "Database Normalization",
    val professorAndDate: String = "Prof. Sarah Chen · 21 Oct 2026",
    val timerText: String = "12:36",
    val status: RecordingStatus = RecordingStatus.RECORDING,
    val statusText: String = "Recording in progress",
    val micConnectedText: String = "Capturing clearly · Microphone connected",
    val tipText: String = "Stay present. Your recording is saved locally while you capture the lecture."
)

package com.example.Simtas.ui.preview

/**
 * UI State for Lecture Preview Screen following MVVM architecture.
 */
data class LecturePreviewUiState(
    val subjectBadge: String = "Computer Science",
    val lectureTitle: String = "Database Normalization",
    val dateDurationCodeText: String = "21 Oct 2026 · 42 min 18 sec · CS301",
    val statusText: String = "Ready to transcribe",
    val currentTimeText: String = "12:36",
    val totalTimeText: String = "42:18",
    val isPlaying: Boolean = false,
    val playbackSpeedText: String = "1x",
    val selectedSubject: String = "Computer Science - CS301",
    val subjectOptions: List<String> = listOf("Computer Science - CS301", "Mathematics - MATH201", "Physics - PHYS101"),
    val fileMetadataText: String = "M4A · 38.4 MB · English · Prof. Sarah Chen",
    val isLoading: Boolean = false
)

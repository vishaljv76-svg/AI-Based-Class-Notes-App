package com.example.Simtas.ui.transcript

data class TranscriptParagraph(
    val timestamp: String,
    val speaker: String,
    val text: String,
    val isHighlighted: Boolean = false
)

/**
 * UI State for Transcript Screen following MVVM architecture.
 */
data class TranscriptUiState(
    val lectureTitle: String = "Database Normalization",
    val language: String = "English",
    val durationAndConfidence: String = "42:18 · 98% confidence",
    val searchQuery: String = "",
    val currentTimeText: String = "12:36",
    val totalTimeText: String = "42:18",
    val isPlaying: Boolean = true,
    val paragraphs: List<TranscriptParagraph> = listOf(
        TranscriptParagraph(
            timestamp = "00:00",
            speaker = "Prof. Sarah Chen",
            text = "Today we'll look at normalization: organizing relational tables to reduce duplicated data and keep updates consistent."
        ),
        TranscriptParagraph(
            timestamp = "04:12",
            speaker = "Prof. Sarah Chen",
            text = "If a student's address is repeated for every enrollment, changing it in just one row creates an update anomaly."
        ),
        TranscriptParagraph(
            timestamp = "12:36",
            speaker = "Prof. Sarah Chen",
            text = "First normal form means each cell holds a single value. A list of courses in one cell is not atomic; use a separate row for each enrollment.",
            isHighlighted = true
        )
    ),
    val excerptFooterText: String = "Excerpt · 00:00–15:20 of 42:18",
    val isLoading: Boolean = false
)

package com.example.Simtas.ui.upload

/**
 * UI State for Upload Lecture Screen following MVVM architecture.
 */
data class UploadLectureUiState(
    val selectedFileName: String = "CS301_Normalization.m4a",
    val fileDurationAndSize: String = "42:18 duration · 38.4 MB",
    val uploadProgressPercent: Int = 78,
    val uploadedBytesText: String = "Uploading · 30.0 of 38.4 MB",
    val isUploading: Boolean = true,
    val isUploadComplete: Boolean = false,
    val isFileSelected: Boolean = true
)

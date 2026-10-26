package com.example.Simtas.ui.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [StudentOnboardingScreen] adhering to MVVM architecture.
 */
class StudentOnboardingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StudentOnboardingUiState())
    val uiState: StateFlow<StudentOnboardingUiState> = _uiState.asStateFlow()

    fun onStudentNameChanged(name: String) {
        _uiState.update { it.copy(studentName = name) }
    }

    fun onCourseSelected(course: String) {
        _uiState.update { it.copy(selectedCourse = course) }
    }

    fun onDepartmentSelected(department: String) {
        _uiState.update { it.copy(selectedDepartment = department) }
    }

    fun onSemesterSelected(semester: String) {
        _uiState.update { it.copy(selectedSemester = semester) }
    }

    fun onContinueClicked() {
        _uiState.update { it.copy(isLoading = true) }
        // Process onboarding step 1 submission
    }
}

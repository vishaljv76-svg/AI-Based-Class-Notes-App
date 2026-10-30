package com.example.Simtas.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Simtas.data.remote.SessionManager
import com.example.Simtas.data.repository.AuthRepository
import com.example.Simtas.data.repository.ProfileRepository
import com.example.Simtas.ui.home.HomeBottomTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for [ProfileSettingsScreen] adhering to MVVM architecture with real API synchronization.
 */
class ProfileSettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileSettingsUiState())
    val uiState: StateFlow<ProfileSettingsUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            val profile = ProfileRepository.fetchProfile()
            if (profile != null) {
                _uiState.update { current ->
                    current.copy(
                        userName = profile.userName,
                        userEmail = profile.userEmail,
                        userInitials = profile.userInitials,
                        courseText = profile.courseText,
                        departmentText = profile.departmentText,
                        semesterBadgeText = profile.semesterBadgeText,
                        yearBadgeText = profile.yearBadgeText
                    )
                }
            } else {
                // Use session details if available
                val name = SessionManager.getUserName()
                val email = SessionManager.getUserEmail()
                _uiState.update { current ->
                    current.copy(
                        userName = name,
                        userEmail = email,
                        userInitials = computeInitials(name)
                    )
                }
            }
        }
    }

    fun toggleStudyReminders(enabled: Boolean) {
        _uiState.update { it.copy(isStudyRemindersEnabled = enabled) }
    }

    fun toggleNotesReadyAlerts(enabled: Boolean) {
        _uiState.update { it.copy(isNotesReadyAlertsEnabled = enabled) }
    }

    fun selectTab(tab: HomeBottomTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onLogoutClicked(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            AuthRepository.logout(null)
            onSuccess()
        }
    }

    fun onEditProfileClicked() {
        _uiState.update { current ->
            current.copy(
                isEditing = true,
                editName = current.userName,
                editCourse = current.courseText,
                editDepartment = current.departmentText,
                editSemester = current.semesterBadgeText
            )
        }
    }

    fun onCancelEditClicked() {
        _uiState.update { it.copy(isEditing = false) }
    }

    fun onEditNameChange(name: String) {
        _uiState.update { it.copy(editName = name) }
    }

    fun onEditCourseChange(course: String) {
        _uiState.update { it.copy(editCourse = course) }
    }

    fun onEditDepartmentChange(department: String) {
        _uiState.update { it.copy(editDepartment = department) }
    }

    fun onEditSemesterChange(semester: String) {
        _uiState.update { it.copy(editSemester = semester) }
    }

    fun onSaveChangesClicked() {
        val current = _uiState.value
        val trimmedName = current.editName.trim().ifEmpty { current.userName }
        val trimmedCourse = current.editCourse.trim().ifEmpty { current.courseText }
        val trimmedDept = current.editDepartment.trim().ifEmpty { current.departmentText }
        val trimmedSem = current.editSemester.trim().ifEmpty { current.semesterBadgeText }

        val initials = computeInitials(trimmedName)
        val yearBadge = computeYearBadge(trimmedSem, current.yearBadgeText)

        // Optimistically update UI
        _uiState.update {
            it.copy(
                userName = trimmedName,
                userInitials = initials,
                courseText = trimmedCourse,
                departmentText = trimmedDept,
                semesterBadgeText = trimmedSem,
                yearBadgeText = yearBadge,
                isEditing = false
            )
        }

        // Asynchronously persist to MySQL backend
        viewModelScope.launch {
            ProfileRepository.saveOrUpdateProfile(
                name = trimmedName,
                course = trimmedCourse,
                department = trimmedDept,
                semester = trimmedSem
            )
        }
    }

    private fun computeInitials(name: String): String {
        val parts = name.split("\\s+".toRegex()).filter { it.isNotBlank() }
        val initials = parts.take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString("")
        return initials.ifEmpty { "U" }
    }

    private fun computeYearBadge(semesterStr: String, fallback: String): String {
        val digits = "\\d+".toRegex().find(semesterStr)?.value?.toIntOrNull()
        return if (digits != null) {
            val year = kotlin.math.ceil(digits / 2.0).toInt()
            when {
                year >= 4 -> "Final year"
                year == 1 -> "1st year"
                year == 2 -> "2nd year"
                year == 3 -> "3rd year"
                else -> "Year $year"
            }
        } else {
            fallback
        }
    }
}

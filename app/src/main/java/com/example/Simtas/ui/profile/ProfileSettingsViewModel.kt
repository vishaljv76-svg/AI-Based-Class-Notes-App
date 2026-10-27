package com.example.Simtas.ui.profile

import androidx.lifecycle.ViewModel
import com.example.Simtas.ui.home.HomeBottomTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [ProfileSettingsScreen] adhering to MVVM architecture.
 */
class ProfileSettingsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileSettingsUiState())
    val uiState: StateFlow<ProfileSettingsUiState> = _uiState.asStateFlow()

    fun toggleStudyReminders(enabled: Boolean) {
        _uiState.update { it.copy(isStudyRemindersEnabled = enabled) }
    }

    fun toggleNotesReadyAlerts(enabled: Boolean) {
        _uiState.update { it.copy(isNotesReadyAlertsEnabled = enabled) }
    }

    fun selectTab(tab: HomeBottomTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun onLogoutClicked() {
        // Logout logic
    }
}

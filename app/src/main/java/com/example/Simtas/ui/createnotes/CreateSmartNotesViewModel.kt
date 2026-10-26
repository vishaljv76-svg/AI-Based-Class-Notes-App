package com.example.Simtas.ui.createnotes

import androidx.lifecycle.ViewModel
import com.example.Simtas.ui.home.HomeBottomTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for [CreateSmartNotesScreen] adhering to MVVM architecture.
 */
class CreateSmartNotesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreateSmartNotesUiState())
    val uiState: StateFlow<CreateSmartNotesUiState> = _uiState.asStateFlow()

    fun selectTab(tab: HomeBottomTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}

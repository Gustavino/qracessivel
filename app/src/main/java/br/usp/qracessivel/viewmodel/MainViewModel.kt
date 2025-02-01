package br.usp.qracessivel.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MainScreenState(
    val detectedContent: String? = null,
    val isProcessing: Boolean = false
)

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()

    fun onQrCodeDetected(content: String) {
        _uiState.value = _uiState.value.copy(
            detectedContent = content,
            isProcessing = false
        )
    }

    fun setProcessing(isProcessing: Boolean) {
        _uiState.value = _uiState.value.copy(isProcessing = isProcessing)
    }

    fun clearDetectedContent() {
        _uiState.value = _uiState.value.copy(detectedContent = null)
    }
}
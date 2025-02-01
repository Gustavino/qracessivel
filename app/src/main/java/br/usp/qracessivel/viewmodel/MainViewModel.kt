package br.usp.qracessivel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.usp.qracessivel.analyzer.QrCodeAnalyzer
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<QrCodeState>(QrCodeState.Scanning)
    val uiState: StateFlow<QrCodeState> = _uiState.asStateFlow()

    private var currentJob: Job? = null

    fun onQrCodeDetected(content: String) {
        currentJob?.cancel()

        currentJob = viewModelScope.launch {
            _uiState.value = QrCodeState.Detected(content)

            delay(QrCodeAnalyzer.CONTENT_TIMEOUT)
            _uiState.value = QrCodeState.Scanning
        }
    }

    fun setProcessing(isProcessing: Boolean) {
        if (isProcessing && _uiState.value is QrCodeState.Scanning) {
            _uiState.value = QrCodeState.Processing
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
    }
}
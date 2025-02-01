package br.usp.qracessivel.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.usp.qracessivel.analyzer.QrCodeAnalyzer
import br.usp.qracessivel.service.AudioFeedbackService
import br.usp.qracessivel.service.TorchService
import br.usp.qracessivel.service.VibrationService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val torchService = TorchService()
    private val vibrationService = VibrationService(application)
    private val audioFeedbackService = AudioFeedbackService(application)

    private val _uiState = MutableStateFlow<QrCodeState>(QrCodeState.Scanning)
    val uiState: StateFlow<QrCodeState> = _uiState.asStateFlow()

    val torchState = torchService.torchState
    val qrCodeAnalyzer = QrCodeAnalyzer(
        onQrCodeDetected = ::onQrCodeDetected,
        onProcessingChanged = ::setProcessing
    )

    private var currentJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
        audioFeedbackService.release()
    }

    fun onQrCodeDetected(content: String) {
        currentJob?.cancel()

        currentJob = viewModelScope.launch {
            _uiState.value = QrCodeState.Detected(content)

            giveDetectionFeedbackToUser()

            delay(QrCodeAnalyzer.CONTENT_TIMEOUT)
            _uiState.value = QrCodeState.Scanning
        }
    }

    fun setProcessing(isProcessing: Boolean) {
        if (isProcessing && _uiState.value is QrCodeState.Scanning) {
            _uiState.value = QrCodeState.Processing
            audioFeedbackService.playProcessingStartSound()
        }
    }

    fun toggleTorch() {
        torchService.toggleTorch()
    }

    fun setCamera(camera: androidx.camera.core.Camera) {
        torchService.setCamera(camera)
    }

    private fun giveDetectionFeedbackToUser() {
        vibrationService.vibrate()
        audioFeedbackService.playSuccessSound()
    }
}
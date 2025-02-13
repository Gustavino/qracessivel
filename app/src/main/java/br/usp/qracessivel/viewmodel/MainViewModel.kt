package br.usp.qracessivel.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.usp.qracessivel.analyzer.QrCodeAnalyzer
import br.usp.qracessivel.model.ResultParser
import br.usp.qracessivel.service.AudioFeedbackService
import br.usp.qracessivel.service.GalleryQrService
import br.usp.qracessivel.service.TorchService
import br.usp.qracessivel.service.VibrationService
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application), MainContract {

    private val torchService = TorchService()
    private val vibrationService = VibrationService(application)
    private val audioFeedbackService = AudioFeedbackService(application)
    private val galleryQrService = GalleryQrService(application)

    private val _uiState = MutableStateFlow<QrCodeState>(QrCodeState.Scanning)
    override val uiState: StateFlow<QrCodeState> = _uiState.asStateFlow()

    override val torchState = torchService.torchState

    private val _events = Channel<MainEvent>()
    override val events = _events.receiveAsFlow()

    override val qrCodeAnalyzer = QrCodeAnalyzer(
        onQrCodeDetected = ::onQrCodeDetected,
        onProcessingChanged = ::setProcessing
    )

    private var currentJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        currentJob?.cancel()
        audioFeedbackService.release()
    }

    override fun onQrCodeDetected(rawContent: String) {
        viewModelScope.launch {
            processQrContent(rawContent)
        }
    }

    override fun setProcessing(isProcessing: Boolean) {
        if (isProcessing && _uiState.value is QrCodeState.Scanning) {
            _uiState.value = QrCodeState.Processing
            audioFeedbackService.playProcessingStartSound()
        }
    }

    override fun toggleTorch() {
        torchService.toggleTorch()
    }

    override fun setCamera(camera: androidx.camera.core.Camera) {
        torchService.setCamera(camera)
    }

    override fun processGalleryImage(uri: Uri) {
        viewModelScope.launch {
            try {
                _uiState.value = QrCodeState.Processing
                val content = galleryQrService.processImage(uri)
                processQrContent(content)
            } catch (e: Exception) {
                _uiState.value = QrCodeState.Error(e.message ?: "Erro ao processar imagem")
            }
        }
    }

    private suspend fun processQrContent(rawContent: String) {
        _uiState.value = QrCodeState.Detected(rawContent)
        giveDetectionFeedbackToUser()
        val parsedContent = ResultParser.parse(rawContent)
        _events.send(MainEvent.QrCodeDetected(parsedContent))
        _uiState.value = QrCodeState.Scanning
    }

    private fun giveDetectionFeedbackToUser() {
        vibrationService.vibrate()
        audioFeedbackService.playSuccessSound()
    }
}
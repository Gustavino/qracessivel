package br.usp.qracessivel.ui.preview

import android.net.Uri
import androidx.camera.core.Camera
import br.usp.qracessivel.analyzer.QrCodeAnalyzerContract
import br.usp.qracessivel.viewmodel.MainContract
import br.usp.qracessivel.viewmodel.MainEvent
import br.usp.qracessivel.viewmodel.QrCodeState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeMainViewModel(
    initialState: QrCodeState = QrCodeState.Scanning
) : MainContract {
    private val _uiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<QrCodeState> = _uiState.asStateFlow()

    private val _torchState = MutableStateFlow(false)
    override val torchState: StateFlow<Boolean> = _torchState.asStateFlow()

    private val _events = Channel<MainEvent>()
    override val events: Flow<MainEvent> = _events.receiveAsFlow()

    override fun onQrCodeDetected(rawContent: String) {
        _uiState.value = QrCodeState.Detected(rawContent)
    }

    override fun setProcessing(isProcessing: Boolean) {
        if (isProcessing && _uiState.value is QrCodeState.Scanning) {
            _uiState.value = QrCodeState.Processing
        }
    }

    override fun toggleTorch() {
        _torchState.value = !_torchState.value
    }

    override fun setCamera(camera: Camera) {
        // No-op para preview
    }

    override fun processGalleryImage(uri: Uri) {
        _uiState.value = QrCodeState.Processing
        // Simula processamento após 1 segundo
        _uiState.value = QrCodeState.Detected("Conteúdo simulado do QR Code")
    }

    override val qrCodeAnalyzer: QrCodeAnalyzerContract = FakeQrCodeAnalyzer()
}
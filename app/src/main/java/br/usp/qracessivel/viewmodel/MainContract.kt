package br.usp.qracessivel.viewmodel

import android.net.Uri
import androidx.camera.core.Camera
import br.usp.qracessivel.analyzer.QrCodeAnalyzerContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MainContract {
    val uiState: StateFlow<QrCodeState>
    val torchState: StateFlow<Boolean>
    val events: Flow<MainEvent>

    fun onQrCodeDetected(rawContent: String)
    fun setProcessing(isProcessing: Boolean)
    fun toggleTorch()
    fun setCamera(camera: Camera)
    fun processGalleryImage(uri: Uri)
    val qrCodeAnalyzer: QrCodeAnalyzerContract
}
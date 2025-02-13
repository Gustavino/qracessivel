package br.usp.qracessivel.ui.preview

import androidx.camera.core.ImageProxy
import br.usp.qracessivel.analyzer.QrCodeAnalyzerContract

class FakeQrCodeAnalyzer: QrCodeAnalyzerContract {
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.close()
    }
}
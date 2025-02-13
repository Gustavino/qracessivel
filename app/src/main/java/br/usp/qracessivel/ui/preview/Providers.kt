package br.usp.qracessivel.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.usp.qracessivel.viewmodel.QrCodeState

class MainScreenStateProvider : PreviewParameterProvider<QrCodeState> {
    override val values = sequenceOf(
        QrCodeState.Scanning,
        QrCodeState.Processing,
        QrCodeState.Detected("https://example.com"),
        QrCodeState.Error("Erro ao processar QR code")
    )
}
package br.usp.qracessivel.viewmodel

import br.usp.qracessivel.model.ResultContent

sealed class QrCodeState {
    object Scanning : QrCodeState()
    object Processing : QrCodeState()
    data class Detected(val content: String) : QrCodeState()
    data class Error(val message: String) : QrCodeState()
}

sealed class MainEvent {
    data class QrCodeDetected(val content: ResultContent) : MainEvent()
}
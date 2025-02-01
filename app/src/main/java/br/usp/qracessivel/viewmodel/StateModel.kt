package br.usp.qracessivel.viewmodel

sealed class QrCodeState {
    object Scanning : QrCodeState()
    object Processing : QrCodeState()
    data class Detected(val content: String) : QrCodeState()
    data class Error(val message: String) : QrCodeState()
}
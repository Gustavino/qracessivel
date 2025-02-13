package br.usp.qracessivel.analyzer

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

interface QrCodeAnalyzerContract : ImageAnalysis.Analyzer {
    override fun analyze(imageProxy: ImageProxy)
}
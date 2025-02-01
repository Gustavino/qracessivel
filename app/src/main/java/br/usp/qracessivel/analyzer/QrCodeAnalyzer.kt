package br.usp.qracessivel.analyzer

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class QrCodeAnalyzer(
    private val onQrCodeDetected: (String) -> Unit,
    private val onProcessingChanged: (Boolean) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            onProcessingChanged(true)
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull()?.rawValue?.let { content ->
                        onQrCodeDetected(content)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Erro ao processar imagem", e)
                }
                .addOnCompleteListener {
                    onProcessingChanged(false)
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    companion object {
        private const val TAG = "QrCodeAnalyzer"
    }
}
package br.usp.qracessivel.service


import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class GalleryQrService(private val context: Context) {
    private val scanner = BarcodeScanning.getClient()

    suspend fun processImage(uri: Uri): String = suspendCancellableCoroutine { continuation ->
        try {
            // Criar InputImage do Uri
            val image = InputImage.fromFilePath(context, uri)

            // Processar imagem com MLKit
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    // Pegar primeiro QR code encontrado
                    barcodes.firstOrNull()?.rawValue?.let { content ->
                        continuation.resume(content)
                    } ?: continuation.resumeWithException(
                        IllegalStateException("Nenhum QR code encontrado")
                    )
                }
                .addOnFailureListener { e ->
                    continuation.resumeWithException(e)
                }

        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}
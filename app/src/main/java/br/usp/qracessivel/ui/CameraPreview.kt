package br.usp.qracessivel.ui

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import br.usp.qracessivel.analyzer.QrCodeAnalyzer
import br.usp.qracessivel.viewmodel.MainViewModel
import java.util.concurrent.Executors

private const val TAG = "CameraPreview"

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    analyzer: QrCodeAnalyzer,
    viewModel: MainViewModel
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }
    var previewUseCase by remember { mutableStateOf<Preview?>(null) }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            PreviewView(ctx).apply {
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        update = { previewView ->
            previewUseCase?.setSurfaceProvider(previewView.surfaceProvider)
        }
    )

    LaunchedEffect(Unit) {
        try {
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                .build()
                .apply {
                    setAnalyzer(executor, analyzer)
                }

            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )
            viewModel.setCamera(camera)

            previewUseCase = preview
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up camera preview", e)
        }
    }
}
package br.usp.qracessivel

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import br.usp.qracessivel.ui.MainScreen
import br.usp.qracessivel.ui.PermissionRequest
import br.usp.qracessivel.ui.theme.QRCodeReaderTheme
import br.usp.qracessivel.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "Camera permission granted")
        } else {
            Log.d(TAG, "Camera permission denied")
        }
    }

    private val galleryLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.processGalleryImage(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRCodeReaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PreMainScreen(
                        hasCameraPermission = checkCameraPermission(),
                        onRequestPermission = { requestCameraPermission() },
                        onGalleryClick = { galleryLauncher.launch("image/*") }
                    )
                }
            }
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

@Composable
fun PreMainScreen(
    hasCameraPermission: Boolean,
    onRequestPermission: () -> Unit,
    onGalleryClick: () -> Unit
) {
    var hasPermission by remember { mutableStateOf(hasCameraPermission) }

    if (hasPermission) {
        MainScreen(
            onGalleryClick = onGalleryClick
        )
    } else {
        PermissionRequest(
            onRequestPermission = {
                onRequestPermission()
                hasPermission = true
            }
        )
    }
}
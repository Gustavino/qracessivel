package br.usp.qracessivel.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.usp.qracessivel.analyzer.QrCodeAnalyzer
import br.usp.qracessivel.viewmodel.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
    onGalleryClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val qrCodeAnalyzer = remember {
        QrCodeAnalyzer(
            onQrCodeDetected = viewModel::onQrCodeDetected,
            onProcessingChanged = viewModel::setProcessing
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Preview da câmera (3/4 superiores da tela)
        CameraPreview(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .semantics {
                    contentDescription = "Preview da câmera. Aponte para um QR code."
                },
            analyzer = qrCodeAnalyzer
        )

        // Área de feedback (1/4 inferior)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status atual
            Text(
                text = when {
                    uiState.isProcessing -> "Processando..."
                    uiState.detectedContent != null -> "QR Code detectado!"
                    else -> "Aponte para um QR Code"
                },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.semantics {
                    contentDescription = when {
                        uiState.isProcessing -> "Processando..."
                        uiState.detectedContent != null -> "QR Code detectado!"
                        else -> "Aponte para um QR Code"
                    }
                }
            )

            // Conteúdo detectado
            uiState.detectedContent?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .semantics {
                            contentDescription = "Conteúdo do QR Code: $it"
                        }
                )
            }
        }

        // Botão de galeria (canto inferior direito)
        FloatingActionButton(
            onClick = onGalleryClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(64.dp)
                .semantics {
                    contentDescription = "Selecionar imagem da galeria"
                }
        ) {
            Icon(
                imageVector = Icons.Default.Photo,
                contentDescription = null
            )
        }
    }
}

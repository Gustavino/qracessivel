package br.usp.qracessivel.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.preview.FakeMainViewModel
import br.usp.qracessivel.ui.preview.previewViewModel
import br.usp.qracessivel.ui.theme.QRCodeReaderTheme
import br.usp.qracessivel.viewmodel.MainContract
import br.usp.qracessivel.viewmodel.MainEvent
import br.usp.qracessivel.viewmodel.QrCodeState
import kotlinx.coroutines.flow.collectLatest

// TODO: testar UI em horizontal.
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainContract,
    onGalleryClick: () -> Unit,
    onQrDetected: (ResultContent) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isTorchOn by viewModel.torchState.collectAsState()

    LaunchedEffect(true) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is MainEvent.QrCodeDetected -> onQrDetected(event.content)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Preview da câmera (3/4 superiores da tela)
        CameraView(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .semantics {
                    contentDescription = "Preview da câmera. Aponte para um QR code."
                },
            analyzer = viewModel.qrCodeAnalyzer,
            setCamera = viewModel::setCamera
        )

        // TODO: alterar essa tela agora que temos resultados em telas individuais. talvez mantê-la para um possível modo contínuo?
        // Área de feedback (1/4 inferior)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val (statusText, contentDescription) = when (uiState) {
                is QrCodeState.Scanning -> "Aponte para um QR Code" to "Aguardando QR Code"
                is QrCodeState.Processing -> "Processando..." to "Processando QR Code"
                is QrCodeState.Detected -> "QR Code detectado!" to "QR Code detectado"
                is QrCodeState.Error -> "Erro ao ler QR Code" to "Erro na leitura"
            }
            Text(
                text = statusText,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.semantics {
                    this.contentDescription = contentDescription
                },
                color = MaterialTheme.colorScheme.onPrimary
            )

            if (uiState is QrCodeState.Detected) {
                Text(
                    text = (uiState as QrCodeState.Detected).content,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .semantics {
                            this.contentDescription =
                                "Conteúdo do QR Code: ${(uiState as QrCodeState.Detected).content}"
                        }
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .fillMaxHeight(0.25f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AccessibleFloatingButton(
                text = "LANTERNA",
                icon = if (isTorchOn) Icons.Default.FlashOff else Icons.Default.FlashOn,
                onClick = viewModel::toggleTorch,
                contentDescription = if (isTorchOn) "Desligar lanterna" else "Ligar lanterna",
                modifier = Modifier.height(72.dp)
            )

            AccessibleFloatingButton(
                text = "GALERIA",
                icon = Icons.Default.Photo,
                onClick = onGalleryClick,
                contentDescription = "Abrir galeria de imagens"
            )
        }
    }
}

@Preview(
    name = "Main Screen - Portrait",
    device = "spec:width=411dp,height=891dp",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F
)
@Preview(
    name = "Main Screen - Portrait - Extra large font",
    device = "spec:width=411dp,height=891dp",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    fontScale = 2f
)
@Composable
fun MainScreenPreviewPortrait(
) {
    QRCodeReaderTheme {
        MainScreen(
            viewModel = FakeMainViewModel(),
            onGalleryClick = {},
            onQrDetected = {},
        )
    }
}

@Preview(
    name = "Main Screen - Landscape",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    device = "spec:width=891dp,height=411dp"
)
@Preview(
    name = "Main Screen - Landscape - Large font",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    device = "spec:width=891dp,height=411dp",
    fontScale = 1.5f
)
@Preview(
    name = "Main Screen - Landscape - Extra large font",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    device = "spec:width=891dp,height=411dp",
    fontScale = 2f
)
@Composable
fun MainScreenPreview() {
    QRCodeReaderTheme {
        MainScreen(
            viewModel = previewViewModel(),
            onGalleryClick = {},
            onQrDetected = {}
        )
    }
}


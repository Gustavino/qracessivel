package br.usp.qracessivel.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.viewmodel.MainEvent
import br.usp.qracessivel.viewmodel.MainViewModel
import br.usp.qracessivel.viewmodel.QrCodeState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
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
        CameraPreview(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .semantics {
                    contentDescription = "Preview da câmera. Aponte para um QR code."
                },
            analyzer = viewModel.qrCodeAnalyzer,
            viewModel = viewModel
        )

        // TODO: alterar essa tela agora que temos resultados em telas individuais. talvez mantê-la para um possível modo contínuo?
        // Área de feedback (1/4 inferior)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.25f)
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

        // Botões flutuantes (canto inferior direito)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Controles da câmera"
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botão da lanterna
            FloatingActionButton(
                onClick = viewModel::toggleTorch,
                modifier = Modifier
                    .size(64.dp)
                    .semantics {
                        contentDescription = if (isTorchOn) {
                            "Desligar lanterna"
                        } else {
                            "Ligar lanterna"
                        }
                    }
            ) {
                Icon(
                    imageVector = if (isTorchOn) Icons.Default.FlashOff else Icons.Default.FlashOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Botão da galeria
            FloatingActionButton(
                onClick = onGalleryClick,
                modifier = Modifier
                    .size(64.dp)
                    .semantics {
                        contentDescription = "Selecionar imagem da galeria"
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Photo,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

package br.usp.qracessivel.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.ui.components.AccessibleFloatingButton

@Composable
fun ResultActionBar(
    rawContent: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AccessibleFloatingButton(
            text = "VOLTAR",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = onDismiss,
            contentDescription = "Voltar para o scanner",
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
        )

        AccessibleFloatingButton(
            text = "COPIAR",
            icon = Icons.Default.ContentCopy,
            onClick = {
                clipboardManager.setText(AnnotatedString(rawContent))
            },
            contentDescription = "Copiar conteúdo",
            modifier = Modifier.weight(1f)
        )

        AccessibleFloatingButton(
            text = "COMPARTILHAR",
            icon = Icons.Default.Share,
            onClick = { /* Implementar compartilhamento */ },
            contentDescription = "Compartilhar conteúdo",
                    modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
        )
    }
}

@Composable
@Preview(fontScale = 2f)
fun ResultActionBarPreview() {
    ResultActionBar(
        rawContent = "Texto de exemplo",
        onDismiss = { /* Implementar ação de voltar */ }
    )
}
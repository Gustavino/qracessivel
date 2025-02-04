package br.usp.qracessivel.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent

@Composable
fun ResultActionBar(
    content: ResultContent,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FloatingActionButton(
            onClick = onDismiss,
            modifier = Modifier.semantics {
                contentDescription = "Voltar para o scanner"
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
        }

        FloatingActionButton(
            onClick = {
                clipboardManager.setText(AnnotatedString(content.rawContent))
            },
            modifier = Modifier.semantics {
                contentDescription = "Copiar conteúdo"
            }
        ) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = null
            )
        }

        FloatingActionButton(
            onClick = { /* Implementar compartilhamento */ },
            modifier = Modifier.semantics {
                contentDescription = "Compartilhar conteúdo"
            }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null
            )
        }
    }
}
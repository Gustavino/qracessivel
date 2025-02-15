package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.components.AccessibleFloatingButton

@Composable
fun UnknownContent(content: ResultContent.Unknown) {
    ContentCard(title = "Conteúdo Não Reconhecido") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = content.rawContent,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            AccessibleFloatingButton(
                icon = Icons.Default.ContentCopy,
                text = "Copiar conteúdo",
                contentDescription = "Copiar conteúdo não reconhecido",
                onClick = { /* Implementar cópia */ }
            )
        }
    }
}
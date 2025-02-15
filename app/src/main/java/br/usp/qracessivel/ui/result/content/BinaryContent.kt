package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.components.AccessibleFloatingButton

@Composable
fun BinaryContent(content: ResultContent.Binary) {
    ContentCard(title = "Dados Binários") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            content.format?.let {
                Text(
                    text = "Formato: $it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Text(
                text = "Tamanho: ${content.data.size} bytes",
                style = MaterialTheme.typography.bodyLarge
            )

            AccessibleFloatingButton(
                icon = Icons.Default.Download,
                text = "Salvar dados",
                contentDescription = "Salvar dados binários",
                onClick = { /* Implementar salvamento */ }
            )
        }
    }
}
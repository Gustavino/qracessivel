package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.components.AccessibleFloatingButton

@Composable
fun EmailContent(content: ResultContent.Email) {
    ContentCard(title = "Email") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = content.address,
                style = MaterialTheme.typography.bodyLarge
            )

            content.subject?.let {
                Text(
                    text = "Assunto: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            content.body?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            AccessibleFloatingButton(
                icon = Icons.AutoMirrored.Filled.Send,
                text = "Enviar email",
                contentDescription = "Enviar email para ${content.address}",
                onClick = { /* Implementar envio de email */ }
            )
        }
    }
}

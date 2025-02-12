package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.components.ActionButton

@Composable
fun MessageContent(content: ResultContent.Message) {
    ContentCard(title = "Mensagem") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = content.number,
                style = MaterialTheme.typography.headlineSmall
            )

            content.message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.semantics {
                        contentDescription = "Mensagem: $it"
                    }
                )
            }

            val isWhatsApp = content.number.startsWith("wa.me/")
            if (isWhatsApp) {
                // todo: refinar caso de whatsapp.
                ActionButton(
                    icon = Icons.AutoMirrored.Filled.Send,
                    text = "Abrir no WhatsApp",
                    contentDescription = "Abrir conversa no WhatsApp",
                    onClick = { /* Implementar abertura do WhatsApp */ }
                )
            } else {
                ActionButton(
                    icon = Icons.AutoMirrored.Filled.Send,
                    text = "Enviar SMS",
                    contentDescription = "Enviar SMS para ${content.number}",
                    onClick = { /* Implementar envio de SMS */ }
                )
            }
        }
    }
}
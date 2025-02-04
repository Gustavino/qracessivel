package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.ActionButton

@Composable
fun PhoneContent(content: ResultContent.Phone) {
    ContentCard(title = "Telefone") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = content.number,
                style = MaterialTheme.typography.headlineSmall
            )

            ActionButton(
                icon = Icons.Default.Phone,
                text = "Ligar",
                contentDescription = "Ligar para ${content.number}",
                onClick = { /* Implementar ação de ligação */ }
            )

            ActionButton(
                icon = Icons.AutoMirrored.Filled.Message,
                text = "Enviar SMS",
                contentDescription = "Enviar SMS para ${content.number}",
                onClick = { /* Implementar ação de SMS */ }
            )
        }
    }
}
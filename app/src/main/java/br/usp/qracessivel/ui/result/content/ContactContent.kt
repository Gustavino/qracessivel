package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.components.AccessibleFloatingButton

@Composable
fun ContactContent(content: ResultContent.Contact) {
    ContentCard(title = "Contato") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = content.name,
                style = MaterialTheme.typography.headlineSmall
            )

            content.phone?.let {
                AccessibleFloatingButton(
                    icon = Icons.Default.Phone,
                    text = it,
                    contentDescription = "Ligar para ${content.name}: $it",
                    onClick = { /* Implementar ação de ligação */ }
                )
            }

            content.email?.let {
                AccessibleFloatingButton(
                    icon = Icons.Default.Email,
                    text = it,
                    contentDescription = "Enviar email para ${content.name}: $it",
                    onClick = { /* Implementar ação de email */ }
                )
            }

            content.address?.let {
                AccessibleFloatingButton(
                    icon = Icons.Default.Place,
                    text = it,
                    contentDescription = "Ver endereço de ${content.name}: $it",
                    onClick = { /* Implementar ação de mapa */ }
                )
            }

            content.organization?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

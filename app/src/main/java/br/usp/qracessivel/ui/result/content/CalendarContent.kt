package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.components.AccessibleFloatingButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CalendarContent(content: ResultContent.CalendarEvent) {
    ContentCard(title = "Evento") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = content.summary,
                style = MaterialTheme.typography.headlineSmall
            )

            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

            Text(
                text = "Início: ${dateFormat.format(Date(content.startTime))}",
                style = MaterialTheme.typography.bodyLarge
            )

            content.endTime?.let {
                Text(
                    text = "Fim: ${dateFormat.format(Date(it))}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            content.location?.let {
                Text(
                    text = "Local: $it",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            content.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            AccessibleFloatingButton(
                icon = Icons.Default.CalendarToday,
                text = "Adicionar ao calendário",
                contentDescription = "Adicionar evento ao calendário",
                onClick = { /* Implementar adição ao calendário */ }
            )

            content.location?.let {
                AccessibleFloatingButton(
                    icon = Icons.Default.Place,
                    text = "Ver local",
                    contentDescription = "Ver localização do evento",
                    onClick = { /* Implementar abertura do mapa */ }
                )
            }
        }
    }
}
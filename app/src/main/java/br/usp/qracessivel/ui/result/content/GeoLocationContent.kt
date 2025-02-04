package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.ActionButton

@Composable
fun GeoLocationContent(content: ResultContent.GeoLocation) {
    ContentCard(title = "Localização") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            content.query?.let {
                Text(
                    text = it, style = MaterialTheme.typography.headlineSmall
                )
            }

            Text(
                text = "Latitude: ${content.latitude}", style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Longitude: ${content.longitude}", style = MaterialTheme.typography.bodyLarge
            )

            ActionButton(icon = Icons.Default.Map,
                text = "Abrir no mapa",
                contentDescription = "Abrir localização no mapa",
                onClick = { /* Implementar abertura no mapa */ })

            ActionButton(icon = Icons.AutoMirrored.Filled.NavigateNext,
                text = "Iniciar navegação",
                contentDescription = "Iniciar navegação até o local",
                onClick = { /* Implementar navegação */ })
        }
    }
}
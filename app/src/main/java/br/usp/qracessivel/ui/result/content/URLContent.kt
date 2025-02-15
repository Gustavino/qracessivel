package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.components.AccessibleFloatingButton

@Composable
fun URLContent(content: ResultContent.Url) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Link detectado",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = content.url,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            AccessibleFloatingButton(
                icon = Icons.AutoMirrored.Filled.OpenInNew,
                text = "Abrir link",
                contentDescription = "Abrir link: ${content.url}",
                onClick = { /* Implementar abertura do link */ }
            )
        }
    }
}

@Preview
@Composable
fun URLContentPreview() {
    URLContent(
        content = ResultContent.Url(
            "https://usp.br",
            url = "https://usp.br",
            timestamp = 129387812837L
        )
    )
}

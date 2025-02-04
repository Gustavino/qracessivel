package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiPassword
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.ActionButton

@Composable
fun WiFiContent(content: ResultContent.WiFi) {
    ContentCard(title = "Rede Wi-Fi") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = content.ssid,
                    style = MaterialTheme.typography.headlineSmall
                )

                Icon(
                    imageVector = if (content.hidden)
                        Icons.Default.VisibilityOff else Icons.Default.Wifi,
                    contentDescription = if (content.hidden)
                        "Rede oculta" else "Rede visível"
                )
            }

            content.encryptionType?.let {
                Text(
                    text = "Tipo de segurança: $it",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (content.password != null) {
                var passwordVisible by remember { mutableStateOf(false) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (passwordVisible) content.password else "••••••••",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.semantics {
                            contentDescription = if (passwordVisible)
                                "Ocultar senha" else "Mostrar senha"
                        }
                    ) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                }
            }

            ActionButton(
                icon = Icons.Default.WifiPassword,
                text = "Conectar à rede",
                contentDescription = "Conectar à rede ${content.ssid}",
                onClick = { /* Implementar conexão Wi-Fi */ }
            )
        }
    }
}
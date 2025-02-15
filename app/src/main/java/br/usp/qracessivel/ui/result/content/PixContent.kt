package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.PixQrContent
import br.usp.qracessivel.ui.components.AccessibleFloatingButton
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PixContent(content: PixQrContent) {
    ContentCard(title = "Pagamento PIX") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TODO: o foco do leitor de tela deve ser column (lê o título da coluna) -> pula para o nome de fato.
            Column {
                Text(
                    text = "Recebedor",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics {
                        contentDescription = "Recebedor"
                    }
                )
                Text(
                    text = content.merchantName,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics {
                        contentDescription = "Nome do recebedor: ${content.merchantName}"
                    }
                )
            }

            Column {
                Text(
                    text = "Cidade",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics {
                        contentDescription = "Cidade"
                    }
                )
                Text(
                    text = content.merchantCity,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics {
                        contentDescription = "Cidade do recebedor: ${content.merchantCity}"
                    }
                )
            }

            content.transactionAmount?.let { amount ->
                val formattedAmount = NumberFormat
                    .getCurrencyInstance(Locale("pt", "BR"))
                    .format(amount)

                Text(
                    text = formattedAmount,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.semantics {
                        contentDescription = "Valor: $formattedAmount"
                    }
                )
            }

            // Chave PIX
            Column {
                Text(
                    text = "Chave PIX",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.semantics {
                        contentDescription = "Chave PIX"
                    }
                )
                Text(
                    text = content.pixKey,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics {
                        contentDescription = "Chave PIX: ${content.pixKey}"
                    }
                )
            }

            // Identificador da transação, se disponível
            content.transactionId?.let { id ->
                Text(
                    text = "ID: $id",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.semantics {
                        contentDescription = "Identificador da transação: $id"
                    }
                )
            }

            // TODO: o foco do leitor de tela deve ser column (lê o título da ação) -> pula apertão o botão.
            AccessibleFloatingButton(
                icon = Icons.Default.ContentCopy,
                text = "Copiar chave PIX",
                contentDescription = "Copiar chave PIX: ${content.pixKey}",
                onClick = { /* Implementar cópia */ }
            )

            AccessibleFloatingButton(
                text = "Pagar no app do banco",
                icon = Icons.Default.AccountBalance,
                onClick = { /* Implementar compartilhamento */ },
                contentDescription = "Compartilhar detalhes do pagamento PIX"
            )
        }
    }
}
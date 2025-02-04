package br.usp.qracessivel.ui.result.content

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.ResultContent

@Composable
fun TextContent(content: ResultContent.Text) {
    Text(
        text = content.rawContent,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .semantics {
                contentDescription = "Texto: ${content.rawContent}"
            }
    )
}

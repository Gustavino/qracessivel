import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.usp.qracessivel.model.PixQrContent
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.result.ResultActionBar
import br.usp.qracessivel.ui.result.content.BinaryContent
import br.usp.qracessivel.ui.result.content.CalendarContent
import br.usp.qracessivel.ui.result.content.ContactContent
import br.usp.qracessivel.ui.result.content.EmailContent
import br.usp.qracessivel.ui.result.content.GeoLocationContent
import br.usp.qracessivel.ui.result.content.PhoneContent
import br.usp.qracessivel.ui.result.content.MessageContent
import br.usp.qracessivel.ui.result.content.PixContent
import br.usp.qracessivel.ui.result.content.TextContent
import br.usp.qracessivel.ui.result.content.URLContent
import br.usp.qracessivel.ui.result.content.UnknownContent
import br.usp.qracessivel.ui.result.content.WiFiContent
import br.usp.qracessivel.ui.result.getTypeLabel

@Composable
fun ResultScreen(
    content: ResultContent,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = content.getTypeLabel(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.semantics {
                    contentDescription = "Tipo de conteúdo: ${content.getTypeLabel()}"
                }
            )
        }

        item {
            when (content) {
                is PixQrContent -> PixContent(content)
                is ResultContent.Text -> TextContent(content)
                is ResultContent.Url -> URLContent(content)
                is ResultContent.Contact -> ContactContent(content)
                is ResultContent.GeoLocation -> GeoLocationContent(content)
                is ResultContent.Email -> EmailContent(content)
                is ResultContent.Phone -> PhoneContent(content)
                is ResultContent.Message -> MessageContent(content)
                is ResultContent.WiFi -> WiFiContent(content)
                is ResultContent.CalendarEvent -> CalendarContent(content)
                is ResultContent.Binary -> BinaryContent(content)
                is ResultContent.Unknown -> UnknownContent(content)
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            ResultActionBar(
                rawContent = content.rawContent,
                onDismiss = onDismiss
            )
        }
    }
}
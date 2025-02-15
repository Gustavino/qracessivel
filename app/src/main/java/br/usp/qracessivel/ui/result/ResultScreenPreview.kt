package br.usp.qracessivel.ui.result

import ResultScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import br.usp.qracessivel.model.PixQrContent
import br.usp.qracessivel.model.ResultContent
import br.usp.qracessivel.ui.preview.PixPreviewProvider
import br.usp.qracessivel.ui.theme.QRCodeReaderTheme


@Composable
@Preview(fontScale = 1.5f)
fun ResultScreenPreview() {
    ResultScreen(
        content = ResultContent.Contact(
            rawContent = "Contato",
            name = "Roberto",
            phone = "+5511987654321",
            email = "roberto@contato.com",
            address = "Rua do Contato, 123",
            organization = "QR Acessivel LTDA",
            title = "Desenvolvedor",
            url = "https://qracessivel.com",
            note = "Nota de contato"
        ),
        onDismiss = {}
    )
}

@Preview(
    name = "PIX Content",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F
)
@Preview(
    name = "PIX Content - Large Font",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    fontScale = 1.5f
)
@Composable
private fun PixContentPreview(
    @PreviewParameter(PixPreviewProvider::class) content: PixQrContent
) {
    QRCodeReaderTheme {
        ResultScreen(
            content = content,
            onDismiss = {}
        )
    }
}

package br.usp.qracessivel.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.usp.qracessivel.model.PixQrContent
import java.math.BigDecimal

class PixPreviewProvider : PreviewParameterProvider<PixQrContent> {
    override val values = sequenceOf(
        // PIX com valor e informações completas
        PixQrContent(
            rawContent = "00020126...", // simplificado para preview
            merchantName = "Loja do João Silva",
            merchantCity = "SAO PAULO",
            postalCode = "04538133",
            transactionAmount = BigDecimal("123.45"),
            transactionId = "PAGAMENTO123",
            pixKey = "joao.silva@email.com"
        ),
        // PIX sem valor (apenas chave)
        PixQrContent(
            rawContent = "00020126...", // simplificado para preview
            merchantName = "Maria's Café",
            merchantCity = "RIO DE JANEIRO",
            postalCode = null,
            transactionAmount = null,
            transactionId = null,
            pixKey = "71912345678"
        ),
        // PIX com chave aleatória
        PixQrContent(
            rawContent = "00020126...", // simplificado para preview
            merchantName = "Mercado do Zé",
            merchantCity = "CURITIBA",
            postalCode = "80250104",
            transactionAmount = BigDecimal("999.99"),
            transactionId = "VENDA202402",
            pixKey = "123e4567-e89b-12d3-a456-426614174000"
        )
    )
}
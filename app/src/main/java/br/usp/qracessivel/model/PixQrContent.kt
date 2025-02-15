package br.usp.qracessivel.model

import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class PixQrContent(
    override val rawContent: String,
    val merchantName: String,
    val merchantCity: String,
    val postalCode: String?,
    val transactionAmount: BigDecimal?,
    val transactionId: String?,
    val pixKey: String,
    override val timestamp: Long = System.currentTimeMillis()
) : ResultContent() {
    /**
     * Valida se o QR Code PIX contém informações mínimas necessárias.
     * Segundo o Manual de Padrões do BACEN, campos obrigatórios são:
     * - Nome do recebedor (merchant name)
     * - Cidade do recebedor (merchant city)
     * - Chave PIX
     */
    fun isValid(): Boolean {
        return merchantName.isNotBlank() &&
                merchantCity.isNotBlank() &&
                pixKey.isNotBlank()
    }

    companion object {
        // Tags EMV para QR codes PIX
        const val TAG_PAYLOAD_FORMAT_INDICATOR = "00"
        const val TAG_MERCHANT_ACCOUNT = "26"
        const val TAG_MERCHANT_NAME = "59"
        const val TAG_MERCHANT_CITY = "60"
        const val TAG_POSTAL_CODE = "61"
        const val TAG_TRANSACTION_AMOUNT = "54"
        const val TAG_TRANSACTION_ID = "62"
    }
}
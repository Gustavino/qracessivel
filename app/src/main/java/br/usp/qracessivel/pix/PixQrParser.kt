package br.usp.qracessivel.pix

import br.usp.qracessivel.model.PixQrContent
import java.math.BigDecimal

/**
 * Parser para QR codes PIX seguindo especificação EMV QRCPS-MPM.
 * TODO: Implementar validações mais robustas:
 * - Validação CRC
 * - Validação de chaves PIX (email, celular, etc)
 * - Validação de campos obrigatórios
 * - Validação de valores monetários
 */
class PixQrParser {
    fun parse(rawContent: String): PixQrContent? {
        if (!isPixQrCode(rawContent)) return null

        val fields = mutableMapOf<String, String>()
        var index = 0

        while (index < rawContent.length) {
            val tag = rawContent.substring(index, index + 2)
            index += 2

            val length = rawContent.substring(index, index + 2).toIntOrNull() ?: break
            index += 2

            if (index + length > rawContent.length) break

            val value = rawContent.substring(index, index + length)
            index += length

            fields[tag] = value
        }

        return try {
            createPixQrContent(rawContent, fields)?.takeIf { it.isValid() }
        } catch (e: Exception) {
            null
        }
    }

    private fun createPixQrContent(
        rawContent: String,
        fields: Map<String, String>
    ): PixQrContent? {
        return PixQrContent(
            rawContent = rawContent,
            merchantName = fields[PixQrContent.TAG_MERCHANT_NAME] ?: return null,
            merchantCity = fields[PixQrContent.TAG_MERCHANT_CITY] ?: return null,
            postalCode = fields[PixQrContent.TAG_POSTAL_CODE],
            transactionAmount = fields[PixQrContent.TAG_TRANSACTION_AMOUNT]?.let {
                BigDecimal(it)
            },
            transactionId = extractTransactionId(fields[PixQrContent.TAG_TRANSACTION_ID]),
            pixKey = extractPixKey(fields[PixQrContent.TAG_MERCHANT_ACCOUNT] ?: return null)
        )
    }

    private fun isPixQrCode(content: String): Boolean {
        return content.length >= 8 &&
                content.startsWith(PixQrContent.TAG_PAYLOAD_FORMAT_INDICATOR) &&
                content.contains(PIX_GUI_PREFIX)
    }

    private fun extractPixKey(merchantAccount: String): String {
        val fields = parseSubfields(merchantAccount)
        return fields.firstOrNull { it.startsWith(PIX_GUI_PREFIX) }
            ?.run { fields.takeIf { it.size > 1 }?.get(1) } ?: ""
    }

    private fun extractTransactionId(transactionField: String?): String? {
        return transactionField?.let { parseSubfields(it).firstOrNull() }
    }

    private fun parseSubfields(field: String): List<String> {
        val results = mutableListOf<String>()
        var index = 0

        while (index < field.length) {
            if (index + 4 > field.length) break

            val length = field.substring(index + 2, index + 4).toIntOrNull() ?: break
            if (index + 4 + length > field.length) break

            results.add(field.substring(index + 4, index + 4 + length))
            index += 4 + length
        }

        return results
    }

    companion object {
        private const val PIX_GUI_PREFIX = "BR.GOV.BCB.PIX"
    }
}
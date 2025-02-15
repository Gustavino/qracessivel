package br.usp.qracessivel.pix

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

/**
 * Testes para o parser de QR codes PIX.
 * TODO: Adicionar testes para:
 * - Validação CRC
 * - Diferentes tipos de chaves PIX (CPF, CNPJ, email, celular)
 * - Formatos inválidos de valores monetários
 * - Caracteres especiais em campos de texto
 */
class PixQrParserTest {
    private lateinit var parser: PixQrParser

    @Before
    fun setup() {
        parser = PixQrParser()
    }

    @Test
    fun `quando QR code PIX é válido, parser retorna objeto PixQrContent`() {
        val rawContent = "00020126580014BR.GOV.BCB.PIX0136123e4567-e12b-12d1-a456-426614174000" +
                "5204000053039865802BR5913Fulano de Tal6008BRASILIA61087007490062070503***6304E2CE"
        val result = parser.parse(rawContent)

        assertNotNull(result)
        result?.let {
            assertEquals("Fulano de Tal", it.merchantName)
            assertEquals("BRASILIA", it.merchantCity)
            assertEquals("70074900", it.postalCode)
            assertEquals("123e4567-e12b-12d1-a456-426614174000", it.pixKey)
        }
    }

    @Test
    fun `quando QR code PIX tem valor, parser extrai valor corretamente`() {
        val rawContent = "00020126460014BR.GOV.BCB.PIX0124fulanodetal@teste.com.br520400005303986540599.005802BR5913Fulano de Tal6009Sao Paulo62140510PAGPARAFUL63045F22"

        val result = parser.parse(rawContent)

        assertNotNull(result)
        result?.let {
            assertEquals(BigDecimal("99.00"), it.transactionAmount)
        }
    }

    @Test
    fun `quando QR code não é PIX, parser retorna null`() {
        val rawContent = "https://example.com"

        val result = parser.parse(rawContent)

        assertNull(result)
    }

    @Test
    fun `quando QR code PIX falta campo obrigatório, parser retorna null`() {
        // QR code sem merchant name
        val rawContent = "00020126580014BR.GOV.BCB.PIX0136123e4567-e12b-12d1-a456-426614174000" +
                "5204000053039865802BR6008BRASILIA6304E2CE"

        val result = parser.parse(rawContent)

        assertNull(result)
    }
}
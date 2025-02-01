package br.usp.qracessivel.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun `quando QR code é detectado, estado é atualizado corretamente`() {
        val qrContent = "https://example.com"

        viewModel.onQrCodeDetected(qrContent)

        val state = viewModel.uiState.value
        assertFalse(state.isProcessing)
        assertEquals(qrContent, state.detectedContent)
    }

    @Test
    fun `quando processamento muda, estado reflete mudança`() {
        viewModel.setProcessing(true)

        val state = viewModel.uiState.value
        assertTrue(state.isProcessing)
    }
}
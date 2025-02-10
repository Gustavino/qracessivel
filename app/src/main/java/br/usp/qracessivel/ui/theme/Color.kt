package br.usp.qracessivel.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Cores definidas seguindo as diretrizes WCAG 2.1:
 * - Contraste mínimo de 4.5:1 para texto normal (AA)
 * - Contraste mínimo de 3:1 para texto grande (AA)
 * - Elementos interativos com contraste mínimo de 3:1
 */

// Cores primárias do tema dark
val DarkPrimary = Color(0xFF6750A4) // Roxo vibrante para elementos principais
val DarkOnPrimary = Color(0xFFFFFFFF) // Branco puro para texto sobre primary

// Cores de superfície e fundo
val DarkBackground = Color(0xFF1C1B1F) // Fundo escuro quase preto
val DarkSurface = Color(0xFF2B2930) // Superfície um pouco mais clara que o fundo
val DarkOnSurface = Color(0xFFE6E1E5) // Branco suave para texto geral

// Cores de erro e alertas
val DarkError = Color(0xFFFF5449) // Vermelho vibrante para erros
val DarkOnError = Color(0xFFFFFFFF) // Branco para texto sobre erro
package br.usp.qracessivel.ui.result

import br.usp.qracessivel.model.ResultContent

fun ResultContent.getTypeLabel(): String {
    return when (this) {
        is ResultContent.Text -> "Texto"
        is ResultContent.Url -> "Link"
        is ResultContent.Contact -> "Contato"
        is ResultContent.GeoLocation -> "Localização"
        is ResultContent.Email -> "Email"
        is ResultContent.Phone -> "Telefone"
        is ResultContent.Message -> "Mensagem"
        is ResultContent.WiFi -> "Rede Wi-Fi"
        is ResultContent.CalendarEvent -> "Evento"
        is ResultContent.Binary -> "Dados Binários"
        is ResultContent.Unknown -> "Conteúdo Desconhecido"
    }
}
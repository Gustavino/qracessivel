package br.usp.qracessivel.model

import android.util.Patterns
import br.usp.qracessivel.pix.PixQrParser
import java.net.URL

// TODO: test each single event.
object ResultParser {
    private val pixParser = PixQrParser()
    fun parse(rawContent: String): ResultContent {
        pixParser.parse(rawContent)?.let { return it }

        return when {
            rawContent.startsWith(ResultContent.VCARD_PREFIX) -> parseVCard(rawContent)

            rawContent.startsWith(ResultContent.MECARD_PREFIX) -> parseMeCard(rawContent)

            rawContent.startsWith(ResultContent.SMS_PREFIX) -> parseMessage(rawContent)

            rawContent.startsWith(ResultContent.WHATSAPP_PREFIX) -> parseMessage(rawContent)

            rawContent.startsWith(ResultContent.TEL_PREFIX) -> parsePhone(rawContent)

            rawContent.startsWith(ResultContent.EMAIL_PREFIX) -> parseEmail(rawContent)

            rawContent.startsWith(ResultContent.GEO_PREFIX) -> parseGeoLocation(rawContent)

            rawContent.startsWith(ResultContent.WIFI_PREFIX) -> parseWiFi(rawContent)

            rawContent.startsWith(ResultContent.CALENDAR_PREFIX) -> parseCalendar(rawContent)

            isValidUrl(rawContent) -> ResultContent.Url(
                rawContent = rawContent,
                url = rawContent
            )

            else -> ResultContent.Text(rawContent)
        }
    }

    private fun parseVCard(content: String): ResultContent.Contact {
        val lines = content.split("\n")
        var name = ""
        var phone: String? = null
        var email: String? = null
        var address: String? = null
        var org: String? = null
        var title: String? = null
        var url: String? = null
        var note: String? = null

        lines.forEach { line ->
            when {
                line.startsWith("FN:") -> name = line.substringAfter("FN:")
                line.startsWith("TEL:") -> phone = line.substringAfter("TEL:")
                line.startsWith("EMAIL:") -> email = line.substringAfter("EMAIL:")
                line.startsWith("ADR:") -> address = line.substringAfter("ADR:")
                line.startsWith("ORG:") -> org = line.substringAfter("ORG:")
                line.startsWith("TITLE:") -> title = line.substringAfter("TITLE:")
                line.startsWith("URL:") -> url = line.substringAfter("URL:")
                line.startsWith("NOTE:") -> note = line.substringAfter("NOTE:")
            }
        }

        return ResultContent.Contact(
            rawContent = content,
            name = name,
            phone = phone,
            email = email,
            address = address,
            organization = org,
            title = title,
            url = url,
            note = note
        )
    }

    private fun parseMeCard(content: String): ResultContent.Contact {
        val data = content.substringAfter(ResultContent.MECARD_PREFIX)
        val fields = data.split(";")
        var name = ""
        var phone: String? = null
        var email: String? = null
        var address: String? = null

        fields.forEach { field ->
            when {
                field.startsWith("N:") -> name = field.substringAfter("N:")
                field.startsWith("TEL:") -> phone = field.substringAfter("TEL:")
                field.startsWith("EMAIL:") -> email = field.substringAfter("EMAIL:")
                field.startsWith("ADR:") -> address = field.substringAfter("ADR:")
            }
        }

        return ResultContent.Contact(
            rawContent = content,
            name = name,
            phone = phone,
            email = email,
            address = address,
            organization = null,
            title = null,
            url = null,
            note = null
        )
    }

    private fun parseMessage(content: String): ResultContent.Message {
        return when {
            content.startsWith(ResultContent.SMS_PREFIX) -> {
                val number = content.substringAfter(ResultContent.SMS_PREFIX)
                    .substringBefore(":")
                val message = if (content.contains(":")) {
                    content.substringAfter(":")
                } else null

                ResultContent.Message(
                    rawContent = content,
                    number = number,
                    message = message
                )
            }
            content.startsWith(ResultContent.WHATSAPP_PREFIX) -> {
                val number = content.substringAfter(ResultContent.WHATSAPP_PREFIX)
                    .substringBefore("?")
                val message = if (content.contains("?text=")) {
                    content.substringAfter("?text=")
                } else null

                ResultContent.Message(
                    rawContent = content,
                    number = "wa.me/$number",
                    message = message
                )
            }
            else -> ResultContent.Message(
                rawContent = content,
                number = content,
                message = null
            )
        }
    }

    private fun parsePhone(content: String): ResultContent.Phone {
        val number = content.substringAfter(ResultContent.TEL_PREFIX)
        return ResultContent.Phone(
            rawContent = content,
            number = number
        )
    }

    private fun parseEmail(content: String): ResultContent.Email {
        val parts = content.substringAfter(ResultContent.EMAIL_PREFIX).split("?")
        val address = parts[0]
        var subject: String? = null
        var body: String? = null

        if (parts.size > 1) {
            parts[1].split("&").forEach { param ->
                when {
                    param.startsWith("subject=") -> subject = param.substringAfter("subject=")
                    param.startsWith("body=") -> body = param.substringAfter("body=")
                }
            }
        }

        return ResultContent.Email(
            rawContent = content,
            address = address,
            subject = subject,
            body = body
        )
    }

    private fun parseGeoLocation(content: String): ResultContent.GeoLocation {
        val coords = content.substringAfter(ResultContent.GEO_PREFIX)
        val parts = coords.split(",")
        val query = if (coords.contains("?q=")) coords.substringAfter("?q=") else null

        return ResultContent.GeoLocation(
            rawContent = content,
            latitude = parts[0].toDouble(),
            longitude = parts[1].substringBefore("?").toDouble(),
            query = query
        )
    }

    private fun parseWiFi(content: String): ResultContent.WiFi {
        val data = content.substringAfter(ResultContent.WIFI_PREFIX)
        val fields = data.split(";")
        var ssid: String? = null
        var pass: String? = null
        var type: String? = null
        var hidden = false

        fields.forEach { field ->
            when {
                field.startsWith("S:") -> ssid = field.substringAfter("S:")
                field.startsWith("P:") -> pass = field.substringAfter("P:")
                field.startsWith("T:") -> type = field.substringAfter("T:")
                field.startsWith("H:") -> hidden = field.substringAfter("H:").toBoolean()
            }
        }

        return ResultContent.WiFi(
            rawContent = content,
            ssid = ssid ?: "",
            password = pass,
            encryptionType = type,
            hidden = hidden
        )
    }

    private fun parseCalendar(content: String): ResultContent.CalendarEvent {
        val lines = content.split("\n")
        var summary = ""
        var startTime = 0L
        var endTime: Long? = null
        var location: String? = null
        var description: String? = null

        lines.forEach { line ->
            when {
                line.startsWith("SUMMARY:") -> summary = line.substringAfter("SUMMARY:")
                line.startsWith("DTSTART:") -> startTime =
                    parseDateTime(line.substringAfter("DTSTART:"))

                line.startsWith("DTEND:") -> endTime = parseDateTime(line.substringAfter("DTEND:"))
                line.startsWith("LOCATION:") -> location = line.substringAfter("LOCATION:")
                line.startsWith("DESCRIPTION:") -> description = line.substringAfter("DESCRIPTION:")
            }
        }

        return ResultContent.CalendarEvent(
            rawContent = content,
            summary = summary,
            startTime = startTime,
            endTime = endTime,
            location = location,
            description = description
        )
    }

    private fun parseDateTime(dateTime: String): Long {
        // Implementação simplificada - em produção usar biblioteca adequada
        return try {
            // Formato básico: YYYYMMDDTHHMMSSZ
            val year = dateTime.substring(0, 4).toInt()
            val month = dateTime.substring(4, 6).toInt() - 1
            val day = dateTime.substring(6, 8).toInt()
            val hour = dateTime.substring(9, 11).toInt()
            val minute = dateTime.substring(11, 13).toInt()
            val second = dateTime.substring(13, 15).toInt()

            java.util.Calendar.getInstance().apply {
                set(year, month, day, hour, minute, second)
            }.timeInMillis
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }

    private fun isValidUrl(urlString: String): Boolean {
        return try {
            URL(urlString)
            Patterns.WEB_URL.matcher(urlString).matches()
        } catch (e: Exception) {
            false
        }
    }
}
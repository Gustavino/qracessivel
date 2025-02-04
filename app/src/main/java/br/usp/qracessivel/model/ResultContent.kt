package br.usp.qracessivel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ResultContent : Parcelable {
    abstract val rawContent: String
    abstract val timestamp: Long

    @Parcelize
    data class Text(
        override val rawContent: String,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class Url(
        override val rawContent: String,
        val url: String,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class Contact(
        override val rawContent: String,
        val name: String,
        val phone: String?,
        val email: String?,
        val address: String?,
        val organization: String?,
        val title: String?,
        val url: String?,
        val note: String?,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class GeoLocation(
        override val rawContent: String,
        val latitude: Double,
        val longitude: Double,
        val query: String?, // Nome do local ou query de busca
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class Email(
        override val rawContent: String,
        val address: String,
        val subject: String?,
        val body: String?,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class Phone(
        override val rawContent: String,
        val number: String,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class Message(
        override val rawContent: String,
        val number: String,
        val message: String?,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class WiFi(
        override val rawContent: String,
        val ssid: String,
        val password: String?,
        val encryptionType: String?, // WEP, WPA, etc.
        val hidden: Boolean = false,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class CalendarEvent(
        override val rawContent: String,
        val summary: String,
        val startTime: Long,
        val endTime: Long?,
        val location: String?,
        val description: String?,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    @Parcelize
    data class Binary(
        override val rawContent: String,
        val data: ByteArray,
        val format: String?, // Identificador do formato dos dados
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Binary
            if (!data.contentEquals(other.data)) return false
            return format == other.format
        }

        override fun hashCode(): Int {
            var result = data.contentHashCode()
            result = 31 * result + (format?.hashCode() ?: 0)
            return result
        }
    }

    @Parcelize
    data class Unknown(
        override val rawContent: String,
        override val timestamp: Long = System.currentTimeMillis()
    ) : ResultContent()

    companion object {
        const val VCARD_PREFIX = "BEGIN:VCARD"
        const val MECARD_PREFIX = "MECARD:"
        const val SMS_PREFIX = "smsto:"
        const val WHATSAPP_PREFIX = "https://wa.me/"
        const val TEL_PREFIX = "tel:"
        const val EMAIL_PREFIX = "mailto:"
        const val GEO_PREFIX = "geo:"
        const val WIFI_PREFIX = "WIFI:"
        const val CALENDAR_PREFIX = "BEGIN:VEVENT"
    }
}
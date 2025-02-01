package br.usp.qracessivel.service

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import br.usp.qracessivel.R

class AudioFeedbackService(context: Context) {

    private val attributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    private val soundPool: SoundPool =
        SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(attributes)
            .build()

    private var successSoundId: Int = 0
    private var errorSoundId: Int = 0
    private var processingStartSoundId: Int = 0

    init {
        successSoundId = soundPool.load(context, R.raw.success_beep, 1)
        errorSoundId = soundPool.load(context, R.raw.error_beep, 1)
        processingStartSoundId = soundPool.load(context, R.raw.processing_beep, 1)
    }

    fun playSuccessSound() {
        soundPool.play(successSoundId, 1f, 1f, 1, 0, 1f)
    }

    fun playErrorSound() {
        soundPool.play(errorSoundId, 1f, 1f, 1, 0, 1f)
    }

    fun playProcessingStartSound() {
        soundPool.play(processingStartSoundId, 0.5f, 0.5f, 1, 0, 1f)
    }

    fun release() {
        soundPool.release()
    }
}
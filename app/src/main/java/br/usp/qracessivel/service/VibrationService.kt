package br.usp.qracessivel.service

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

class VibrationService(context: Context) {
    private val vibrator: Vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun vibrate(duration: Long = DEFAULT_DURATION) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(
                duration,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }

    companion object {
        private const val DEFAULT_DURATION = 100L
    }
}
package dev.clover.gamehub.features.brightness

import android.content.Context
import android.provider.Settings
import kotlin.math.roundToInt

class BrightnessController(private val context: Context) {
	fun hasWriteAccess(): Boolean = Settings.System.canWrite(context)

	fun getBrightness(): Float {
		val value = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 128)
		return value / 255f
	}

	fun setBrightness(brightness: Float) {
		if (!hasWriteAccess()) return
		val clamped = brightness.coerceIn(0f, 1f)
		Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, (clamped * 255f).roundToInt())
	}
}
package dev.clover.gamehub.features.rotation

import android.content.Context
import android.provider.Settings

class RotationController(private val context: Context) {
	fun hasWriteAccess(): Boolean = Settings.System.canWrite(context)

	fun isAutoRotateEnabled(): Boolean =
		Settings.System.getInt(context.contentResolver, Settings.System.ACCELEROMETER_ROTATION, 1) == 1

	fun setAutoRotate(enabled: Boolean) {
		if (!hasWriteAccess()) return
		Settings.System.putInt(
			context.contentResolver,
			Settings.System.ACCELEROMETER_ROTATION,
			if (enabled) 1 else 0
		)
	}
}
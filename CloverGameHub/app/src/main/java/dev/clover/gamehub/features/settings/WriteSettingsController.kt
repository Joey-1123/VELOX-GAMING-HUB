package dev.clover.gamehub.features.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

object WriteSettingsController {
	fun hasWriteSettings(context: Context): Boolean = Settings.System.canWrite(context)

	fun openWriteSettings(context: Context) {
		val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
			data = Uri.parse("package:" + context.packageName)
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		}
		context.startActivity(intent)
	}
}
package dev.clover.gamehub.features.overlay

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

object OverlayController {
	fun canDrawOverlays(context: Context): Boolean = Settings.canDrawOverlays(context)

	fun openOverlaySettings(context: Context) {
		val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.packageName))
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		context.startActivity(intent)
	}
}
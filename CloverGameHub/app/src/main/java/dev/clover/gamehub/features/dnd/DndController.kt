package dev.clover.gamehub.features.dnd

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings

class DndController(private val context: Context) {
	private val notificationManager: NotificationManager =
		context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

	fun hasPolicyAccess(): Boolean = notificationManager.isNotificationPolicyAccessGranted

	fun openPolicyAccessSettings() {
		context.startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS).apply {
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		})
	}

	fun setDndEnabled(enabled: Boolean) {
		if (!hasPolicyAccess()) return
		notificationManager.setInterruptionFilter(
			if (enabled) NotificationManager.INTERRUPTION_FILTER_NONE
			else NotificationManager.INTERRUPTION_FILTER_ALL
		)
	}

	fun isDndEnabled(): Boolean {
		return notificationManager.currentInterruptionFilter == NotificationManager.INTERRUPTION_FILTER_NONE
	}
}
package dev.clover.gamehub.features.overlay

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class OverlayService : Service() {
	override fun onBind(intent: Intent?): IBinder? = null

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		startForeground(42, buildNotification())
		// TODO: draw overlay using WindowManager + TYPE_APPLICATION_OVERLAY
		return START_STICKY
	}

	private fun buildNotification(): Notification {
		val channelId = "overlay"
		val manager = getSystemService(NotificationManager::class.java)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val ch = NotificationChannel(channelId, "Overlay", NotificationManager.IMPORTANCE_LOW)
			manager.createNotificationChannel(ch)
		}
		return NotificationCompat.Builder(this, channelId)
			.setContentTitle("FPS Overlay Running")
			.setSmallIcon(android.R.drawable.stat_sys_warning)
			.setOngoing(true)
			.build()
	}
}
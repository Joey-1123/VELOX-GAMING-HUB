package dev.clover.gamehub.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.clover.gamehub.features.dnd.DndController
import dev.clover.gamehub.features.rotation.RotationController
import dev.clover.gamehub.features.brightness.BrightnessController
import dev.clover.gamehub.features.overlay.OverlayController

@Composable
fun SettingsScreen(contentPadding: PaddingValues) {
	val context = LocalContext.current
	val dnd = remember { DndController(context) }
	val rotation = remember { RotationController(context) }
	val brightness = remember { BrightnessController(context) }
	var hasAccess by remember { mutableStateOf(dnd.hasPolicyAccess()) }
	var dndOn by remember { mutableStateOf(dnd.isDndEnabled()) }
	var hasWrite by remember { mutableStateOf(dev.clover.gamehub.features.settings.WriteSettingsController.hasWriteSettings(context)) }
	var autoRotate by remember { mutableStateOf(rotation.isAutoRotateEnabled()) }
	var bright by remember { mutableStateOf(brightness.getBrightness()) }
	var canOverlay by remember { mutableStateOf(OverlayController.canDrawOverlays(context)) }

	LaunchedEffect(Unit) {
		hasAccess = dnd.hasPolicyAccess()
		dndOn = dnd.isDndEnabled()
		hasWrite = dev.clover.gamehub.features.settings.WriteSettingsController.hasWriteSettings(context)
		autoRotate = rotation.isAutoRotateEnabled()
		bright = brightness.getBrightness()
		canOverlay = OverlayController.canDrawOverlays(context)
	}

	Column(
		modifier = Modifier.fillMaxSize().padding(contentPadding).padding(16.dp),
		horizontalAlignment = Alignment.Start,
		verticalArrangement = Arrangement.spacedBy(16.dp)
	) {
		Text("Settings", style = MaterialTheme.typography.headlineSmall)
		if (!hasAccess) {
			Text("Grant Do Not Disturb control to enable quick toggling.")
			Button(onClick = { dnd.openPolicyAccessSettings() }) { Text("Grant DND access") }
		} else {
			Text("Do Not Disturb")
			Switch(checked = dndOn, onCheckedChange = {
				dnd.setDndEnabled(it)
				dndOn = dnd.isDndEnabled()
			})
		}

		Text("System Settings Access")
		if (!hasWrite) {
			Button(onClick = { dev.clover.gamehub.features.settings.WriteSettingsController.openWriteSettings(context) }) { Text("Grant Settings write access") }
		}

		Text("Auto-rotate")
		Switch(checked = autoRotate, onCheckedChange = {
			rotation.setAutoRotate(it)
			autoRotate = rotation.isAutoRotateEnabled()
		})

		Text("Brightness")
		Slider(value = bright, onValueChange = {
			bright = it
			brightness.setBrightness(it)
		})

		Text("FPS Overlay")
		if (!canOverlay) {
			Button(onClick = { OverlayController.openOverlaySettings(context) }) { Text("Grant overlay") }
		} else {
			Text("Overlay permission granted")
		}
	}
}
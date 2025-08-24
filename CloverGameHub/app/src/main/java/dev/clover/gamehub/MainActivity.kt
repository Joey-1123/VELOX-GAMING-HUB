package dev.clover.gamehub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			dev.clover.gamehub.ui.theme.CloverTheme {
				CloverGameHubApp()
			}
		}
	}
}

private data class Dest(
	val route: String,
	val label: String,
	val icon: @Composable () -> Unit
)

@Composable
fun CloverGameHubApp() {
	val navController = rememberNavController()
	val dests = listOf(
		Dest("home", "Home") { Icon(Icons.Default.Home, contentDescription = null) },
		Dest("wallpapers", "Walls") { Icon(Icons.Default.Image, contentDescription = null) },
		Dest("settings", "Settings") { Icon(Icons.Default.Settings, contentDescription = null) }
	)
	Scaffold(
		bottomBar = {
			NavigationBar {
				val navBackStackEntry by navController.currentBackStackEntryAsState()
				val currentRoute = navBackStackEntry?.destination?.route
				dests.forEach { dest ->
					NavigationBarItem(
						selected = currentRoute == dest.route,
						onClick = {
							navController.navigate(dest.route) {
								popUpTo(navController.graph.findStartDestination().id) { saveState = true }
								launchSingleTop = true
								restoreState = true
							}
						},
						icon = dest.icon,
						label = { Text(dest.label) }
					)
				}
			}
		}
	) { padding ->
		NavHost(navController, startDestination = "home", modifier = Modifier.fillMaxSize()) {
			composable("home") { SimpleScreen("Welcome to Clover Game Hub") }
			composable("wallpapers") {
				dev.clover.gamehub.features.wallpapers.WallpapersScreen(contentPadding = padding)
			}
			composable("settings") {
				dev.clover.gamehub.features.settings.SettingsScreen(contentPadding = padding)
			}
		}
	}
}

@Composable
private fun SimpleScreen(title: String) {
	Text(title, style = MaterialTheme.typography.headlineSmall)
}
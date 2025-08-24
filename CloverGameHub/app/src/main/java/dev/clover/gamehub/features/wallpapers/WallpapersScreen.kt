package dev.clover.gamehub.features.wallpapers

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.clover.gamehub.R
import kotlinx.coroutines.launch

private data class Wallpaper(val name: String, val resId: Int)

@Composable
fun WallpapersScreen(contentPadding: PaddingValues) {
	val context = LocalContext.current
	val walls = listOf(
		Wallpaper("Aurora", R.drawable.gradient_wall_1),
		Wallpaper("Sunset", R.drawable.gradient_wall_2),
		Wallpaper("Ocean", R.drawable.gradient_wall_3)
	)
	val snackbarHostState = remember { SnackbarHostState() }
	val scope = rememberCoroutineScope()

	Column(Modifier.fillMaxSize().padding(contentPadding)) {
		Text("Wallpapers", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
		LazyVerticalGrid(
			columns = GridCells.Fixed(2),
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(12.dp),
			contentPadding = PaddingValues(12.dp)
		) {
			items(walls) { wall ->
				Image(
					painter = painterResource(id = wall.resId),
					contentDescription = wall.name,
					modifier = Modifier
						.aspectRatio(9f/16f)
						.clickable {
							setWallpaperFromDrawable(context, wall.resId)
							scope.launch { snackbarHostState.showSnackbar("Applied ${wall.name}") }
						}
				)
			}
		}
		SnackbarHost(hostState = snackbarHostState)
	}
}

private fun setWallpaperFromDrawable(context: Context, resId: Int) {
	val wm = WallpaperManager.getInstance(context)
	val displayMetrics = context.resources.displayMetrics
	val width = displayMetrics.widthPixels
	val height = displayMetrics.heightPixels
	val drawable: Drawable = context.getDrawable(resId) ?: return
	drawable.setBounds(0, 0, width, height)
	val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
	val canvas = Canvas(bitmap)
	drawable.draw(canvas)
	wm.setBitmap(bitmap)
}
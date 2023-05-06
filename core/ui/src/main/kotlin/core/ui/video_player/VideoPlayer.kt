package core.ui.video_player

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoPlayer(modifier: Modifier = Modifier, url: String,containerColor:Color = Color.Black) {
    val context = LocalContext.current
    val exoPlayer = remember(url) {
        ExoPlayer.Builder(context).build().apply {
            Log.i("VideoPlayer", "url: $url")
            setMediaItem(MediaItem.fromUri(url))
            prepare()
        }
    }
    Box(modifier = modifier) {
        AndroidView(modifier = Modifier.fillMaxSize().background(containerColor), factory = { context ->
            val view = StyledPlayerView(context)
                .apply {
                    player = exoPlayer
                    setFullscreenButtonClickListener { context.startFullScreenVideoPlayer(url) }
                }
            view
        }, update = {})
    }
}


fun Context.startFullScreenVideoPlayer(url: String) {
    val intent = Intent(this, FullScreenVideoActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        putExtra("url", url)
    }
    with(this) {
        this.startActivity(intent)
    }
}
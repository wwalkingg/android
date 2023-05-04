package feature.calendar

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class FullScreenVideoActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val exoPlayer = remember {
                ExoPlayer.Builder(context).build()
            }
            val player = exoPlayer
            MaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
                        val view = StyledPlayerView(context)
                        view.player = player
                        view.setFullscreenButtonClickListener {
                            player.pause()
                            this@FullScreenVideoActivity.finish()
                        }
                        view
                    }, update = {
                        player.pause()
                        val mediaItem: MediaItem =
                            MediaItem.fromUri("https://media.w3.org/2010/05/sintel/trailer.mp4")
                        player.setMediaItem(mediaItem)
                        player.prepare()
                    })
                }
            }
        }
    }
}
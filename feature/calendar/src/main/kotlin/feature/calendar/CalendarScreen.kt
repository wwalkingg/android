package feature.calendar

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.icu.util.Calendar
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(component: CalendarComponent) {
    Scaffold { padding ->
        var dateTime by remember {
            mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
        }
        Column(Modifier.padding(padding)) {
            AndroidView(modifier = Modifier.fillMaxWidth(), factory = { context ->
                val view = CalendarView(context)
                view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    dateTime = LocalDate(year, month + 1, dayOfMonth)
                }
                view.firstDayOfWeek = Calendar.MONDAY
                view
            }, update = {})
            val shape = remember {
                RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            }
            Box(
                modifier = Modifier
                    .shadow(10.dp, shape)
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(20.dp, 10.dp)
            ) {
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "为您推荐",
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier.alignByBaseline()
                            )
                            Text(text = dateTime.toString(), modifier = Modifier.alignByBaseline())
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        val context = LocalContext.current
                        val player = remember {
                            ExoPlayer.Builder(context).build()
                        }
                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.medium)
                                .background(Color.Black)
                        ) {
                            AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
                                val view = StyledPlayerView(context)
                                view.player = player
                                view.setFullscreenButtonClickListener {
                                    val intent = Intent(context, FullScreenVideoActivity::class.java).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                        putExtra("url", "")
                                    }
                                    with(context.findActivity()) {
                                        this?.startActivity(intent)
                                    }
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
    }
}


@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(orientation) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}